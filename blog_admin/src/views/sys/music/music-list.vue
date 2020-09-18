<template>
  <div>
    <!-- 搜索栏 -->
    <el-form :inline="true" :model="page" class="demo-form-inline" size="mini">
      <el-form-item label="歌曲名">
        <el-input v-model="page.params.name" placeholder="歌曲名" clearable />
      </el-form-item>
      <el-form-item label="歌手">
        <el-input v-model="page.params.artist" placeholder="歌手" clearable />
      </el-form-item>
      <el-form-item label="启用">
        <el-select v-model="page.params.enabled" placeholder="启用" clearable filterable>
          <el-option label="启用" :value="1" />
          <el-option label="未启用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" sizi="mini" @click="getByPage">查询</el-button>
      </el-form-item>
    </el-form>
    <el-divider />
    <div class="add-container">
      <el-button type="primary" class="add-button" size="mini" @click="openAddDialog">添加</el-button>
      <audio :src="musicUrl" :autoplay="autoplay" controls="controls" />
    </div>
    <!-- 列表 -->
    <el-table :data="page.list" border style="width: 100%" @sort-change="changeSort">
      <el-table-column prop="id" label="编号" />
      <el-table-column prop="name" label="歌曲名" width="200" show-overflow-tooltip />
      <el-table-column prop="artist" label="歌手" sortable="custom" />
      <el-table-column prop="cover" label="图片" width="120">
        <template slot-scope="scope">
          <el-image
            style="width: 100%;height: 100px"
            :src="scope.row.cover"
            :preview-src-list="[scope.row.cover]"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createdTime" label="创建时间" sortable="custom" />
      <el-table-column prop="enabled" label="启用" sortable="custom">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enabled === 1">启用</el-tag>
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
                <el-button size="mini" type="primary" @click="handleEdit(scope.row.id)">编辑</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button size="mini" type="primary" @click="readLrc(scope.row.id)">查看歌词</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button size="mini" type="primary" @click="toHear(scope.row.url)">试听</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button v-if="scope.row.enabled === 0" size="mini" type="success" @click="toEnable(scope.row.id)">启用</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button v-if="scope.row.enabled === 1" size="mini" type="warning" @click="toDisable(scope.row.id)">弃用</el-button>
              </el-dropdown-item>
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
      <music-add @closeAddDialog="closeAddDialog" @getByPage="getByPage" />
    </el-dialog>

    <!-- 修改弹窗 -->
    <el-dialog title="修改" :visible.sync="updateDialog">
      <music-update :music="music" @closeUpdateDialog="closeUpdateDialog" @getByPage="getByPage" />
    </el-dialog>

    <!-- 歌词弹窗 -->
    <el-dialog title="修改" :visible.sync="lrcDialog">
      <div v-html="music.lrc" />
    </el-dialog>
  </div>
</template>

<script>
import musicApi from '@/api/music'
import MusicAdd from './music-add'
import MusicUpdate from './music-update'
export default {
  components: {
    MusicAdd,
    MusicUpdate
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
      music: {},
      musicUrl: '', // 音乐预览url
      autoplay: '', // 自动播放
      updateDialog: false, // 控制修改弹窗显示
      lrcDialog: false, // 控制修改弹窗显示
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
        musicApi.enableById(id).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      })
    },
    toHear(url) {
      this.musicUrl = url
      this.autoplay = true
    },
    toDisable(id) {
      // 弃用
      this.$confirm('是否弃用?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        musicApi.disableById(id).then(res => {
          this.$message.success(res.msg)
          this.getByPage()
        })
      })
    },
    getByPage() {
      musicApi.getByPage(this.page).then(res => {
        this.page = res.data
      })
    },
    handleEdit(id) {
      // 修改
      musicApi.get(id).then(res => {
        this.music = res.data
        this.updateDialog = true
      })
    },
    readLrc(id) {
      // 阅读歌词
      musicApi.get(id).then(res => {
        this.music = res.data
        this.lrcDialog = true
      })
    },
    handleDelete(id) {
      // 删除
      this.$confirm('是否删除该博客?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        musicApi.deleteById(id).then(res => {
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
<style scoped>
.add-container {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.add-button {
  margin-top: 5px;
  margin-right: 10px;
}
</style>
