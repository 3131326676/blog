<template>
  <div>
    <!-- 搜索栏 -->
    <el-form :inline="true" :model="page" class="demo-form-inline" size="mini">
      <el-form-item label="博客标题">
        <el-input v-model="page.params.blogTitle" placeholder="博客标题" clearable />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="page.params.nickname" placeholder="昵称" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" sizi="mini" @click="getByPage">查询</el-button>
      </el-form-item>
    </el-form>
    <el-divider />

    <!-- 列表 -->
    <el-table :data="page.list" border style="width: 100%">
      <el-table-column prop="blog.blogTitle" label="标题" show-overflow-tooltip />
      <el-table-column prop="user.nickname" label="评价人" show-overflow-tooltip />
      <el-table-column prop="commentContent" label="评价内容" show-overflow-tooltip />
      <el-table-column prop="commentGood" label="点赞数" width="50" />
      <el-table-column prop="createdTime" label="评价时间" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button type="primary" size="mini">
              操作
              <i class="el-icon-arrow-down el-icon--right" />
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
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
      <blog-add @closeAddDialog="closeAddDialog" @getByPage="getByPage" />
    </el-dialog>
  </div>
</template>

<script>
import commentApi from '@/api/comment'
export default {
  data() {
    return {
      page: {
        currentPage: 1, // 当前页
        pageSize: 10, // 每页显示条数
        totalPage: 0, // 总页数
        totalCount: 0, // 总条数
        params: {}, // 查询参数对象
        list: [] // 数据
      }
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
      commentApi.getByPage(this.page).then(res => {
        this.page = res.data
      })
    },
    handleDelete(id) {
      // 删除
      this.$confirm('是否删除该评论?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        commentApi.deleteById(id).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      })
    }
  }
}
</script>
