<template>
  <div v-loading="loading">
    <!-- 搜索栏 -->
    <el-form :inline="true" :model="page" class="demo-form-inline" size="mini">
      <el-form-item label="请求地址">
        <el-input v-model="page.params.logUrl" placeholder="请求地址" clearable />
      </el-form-item>
      <el-form-item label="请求ip">
        <el-input v-model="page.params.logIp" placeholder="请求ip" clearable />
      </el-form-item>
      <el-form-item label="请求状态">
        <el-select v-model="page.params.logStatus" placeholder="请求状态" clearable filterable>
          <el-option label="正常" :value="1" />
          <el-option label="异常" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="请求方式">
        <el-select v-model="page.params.logMethod" placeholder="请求方式" clearable filterable>
          <el-option label="GET" value="GET" />
          <el-option label="POST" value="POST" />
          <el-option label="PUT" value="PUT" />
          <el-option label="DELETE" value="DELETE" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" sizi="mini" @click="getByPage">查询</el-button>
      </el-form-item>
    </el-form>
    <el-divider />
    <el-button type="danger" class="add-button" size="mini" @click="deleteByIds">批量删除</el-button>
    <el-button type="primary" class="add-button" size="mini" @click="exportAll">全部导出</el-button>

    <!-- 列表 -->
    <el-table
      :data="page.list"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
      @sort-change="changeSort"
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column prop="logId" label="编号" />
      <el-table-column prop="logUrl" label="请求地址" sortable="custom" />
      <el-table-column prop="logParams" label="参数" width="200" show-overflow-tooltip />
      <el-table-column prop="logStatus" label="请求状态" sortable="custom">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.logStatus === 1" type="success">正常</el-tag>
          <el-tag v-else type="danger">异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="logMethod" label="请求方式" sortable="custom" />
      <el-table-column prop="logTime" label="响应时间（毫秒）" sortable="custom" />
      <el-table-column prop="logIp" label="请求ip" />
      <el-table-column prop="logResult" label="返回值" width="200" show-overflow-tooltip />
      <el-table-column prop="createdTime" label="创建时间" sortable="custom" />
    </el-table>

    <!-- 分页 -->
    <el-pagination
      class="pagination"
      :current-page="page.currentPage"
      :page-sizes="[10,20,50,100]"
      :page-size="page.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.totalCount"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

  </div>
</template>

<script>
import logApi from '@/api/log'
export default {
  data() {
    return {
      page: {
        currentPage: 1, // 当前页
        pageSize: 10, // 每页显示条数
        totalPage: 0, // 总页数
        totalCount: 0, // 总条数
        params: {}, // 查询参数对象
        list: [], // 数据
        sortColumn: '', // 排序列
        sortMethod: 'asc' // 排序方式
      },
      loading: false, // 控制是否显示加载效果
      selectLogs: [] // 被选中的日志
    }
  },
  created() {
    this.getByPage()
  },
  methods: {
    handleSizeChange(val) {
      this.page.pageSize = val
      this.getByPage()
    },
    handleCurrentChange(val) {
      this.page.currentPage = val
      this.getByPage()
    },
    getByPage() {
      logApi.getByPage(this.page).then(res => {
        this.page = res.data
      })
    },
    handleSelectionChange(val) {
      this.selectLogs = val
    },
    exportAll() {
      this.loading = true
      logApi.exportExcel().then(res => {
        const blob = new Blob([res], { type: 'application/vnd.ms-excel' })
        const elink = document.createElement('a')
        elink.download = '系统日志.xlsx'
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href)
        document.body.removeChild(elink)
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    deleteByIds() {
      // 批量删除
      this.$confirm('删除之后无法恢复，是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        const logIds = []
        this.selectLogs.forEach(e => {
          logIds.push(e.logId)
        })
        logApi.deleteByIds(logIds).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      })
    },
    changeSort(e) {
      if (e.order) {
        this.page.sortColumn = e.prop
        this.page.sortMethod = e.order
      } else {
        this.page.sortColumn = ''
        this.page.sortMethod = 'asc'
      }
      this.getByPage()
    }
  }
}
</script>
