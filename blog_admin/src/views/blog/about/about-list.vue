<template>
  <div>
    <!-- 搜索栏 -->
    <el-form :inline="true" :model="page" class="demo-form-inline" size="mini">
      <el-form-item label="标题">
        <el-input v-model="page.params.aboutTitle" placeholder="标题" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" sizi="mini" @click="getByPage">查询</el-button>
      </el-form-item>
    </el-form>
    <el-divider />
    <el-button type="primary" class="add-button" size="mini" @click="openAddDialog">添加</el-button>

    <!-- 列表 -->
    <el-table :data="page.list" border style="width: 100%" @sort-change="changeSort">
      <el-table-column prop="aboutId" label="变高" />
      <el-table-column prop="aboutTitle" label="标题" width="200" show-overflow-tooltip />
      <el-table-column prop="aboutRead" label="阅读数" sortable="custom" />
      <el-table-column prop="createdTime" label="创建时间" sortable="custom" />
      <el-table-column prop="updateTime" label="修改时间" sortable="custom" />
      <el-table-column prop="enable" label="启用">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enable === 1">启用</el-tag>
          <el-tag v-else type="info">未启用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="primary" size="mini">
              操作
              <i class="el-icon-arrow-down el-icon--right" />
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button size="mini" type="primary" @click="handleEdit(scope.row.aboutId)">编辑</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button size="mini" type="primary" @click="toRead(scope.row.aboutId)">查看</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button v-if="scope.row.enable === 0" size="mini" type="success" @click="toEnable(scope.row.aboutId)">启用</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button v-if="scope.row.enable === 1" size="mini" type="warning" @click="toDisable(scope.row.aboutId)">弃用</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button size="mini" type="danger" @click="handleDelete(scope.row.aboutId)">删除</el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
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

    <!-- 添加弹窗 -->
    <el-dialog title="添加" :visible.sync="addDialog">
      <about-add @closeAddDialog="closeAddDialog" @getByPage="getByPage" />
    </el-dialog>

    <!-- 阅读弹窗 -->
    <el-dialog title="阅读" :visible.sync="readDialog" width="50%">
      <div v-html="about.aboutContent" />
    </el-dialog>
    <!-- 修改弹窗 -->
    <el-dialog title="修改" :visible.sync="updateDialog">
      <about-update :about="about" @closeUpdateDialog="closeUpdateDialog" @getByPage="getByPage" />
    </el-dialog>
  </div>
</template>

<script>
import aboutApi from '@/api/about'
import AboutAdd from './about-add'
import AboutUpdate from './about-update'
export default {
  components: {
    AboutAdd,
    AboutUpdate
  },
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
      about: {},
      updateDialog: false, // 控制修改弹窗显示
      readDialog: false, // 控制阅读弹窗显示
      addDialog: false // 控制添加弹窗显示
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
    changeSort(e) {
      if (e.order) {
        this.page.sortColumn = e.prop
        this.page.sortMethod = e.order
      } else {
        this.page.sortColumn = ''
        this.page.sortMethod = 'asc'
      }
      this.getByPage()
    },
    toEnable(id) {
      // 启用
      this.$confirm('是否启用?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        aboutApi.enableById(id).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      })
    },
    toDisable(id) {
      // 弃用
      this.$confirm('是否弃用?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        aboutApi.disableById(id).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      })
    },
    getByPage() {
      aboutApi.getByPage(this.page).then(res => {
        this.page = res.data
      })
    },
    handleEdit(id) {
      // 修改
      aboutApi.get(id).then(res => {
        this.about = res.data
        this.updateDialog = true
      })
    },
    toRead(id) {
      aboutApi.get(id).then(res => {
        this.about = res.data
        this.readDialog = true
      })
    },
    handleDelete(id) {
      // 删除
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        aboutApi.deleteById(id).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      })
    },
    openAddDialog() {
      // 打开添加弹窗
      this.addDialog = true
    },
    closeAddDialog() {
      // 关闭添加弹窗
      this.addDialog = false
    },
    closeUpdateDialog() {
      // 关闭修改弹窗
      this.updateDialog = false
    }
  }
}
</script>
