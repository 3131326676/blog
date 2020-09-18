<template>
  <div>
    <el-button type="primary" class="add-button" size="mini" @click="openAddDrawer">添加</el-button>

    <!-- 列表 -->
    <el-table :data="linkList" border style="width: 100%">
      <el-table-column prop="linkId" label="编号" />
      <el-table-column prop="linkName" label="分类" />
      <el-table-column prop="linkUrl" label="链接地址" />
      <el-table-column prop="createdTime" label="创建时间" />
      <el-table-column prop="updateTime" label="修改时间" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-dropdown>
            <el-button link="primary" size="mini">
              操作
              <i class="el-icon-arrow-down el-icon--right" />
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button size="mini" type="primary" @click="handleEdit(scope.row.linkId)">编辑</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button size="mini" type="danger" @click="handleDelete(scope.row.linkId)">删除</el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加抽屉 -->
    <el-drawer title="添加" :visible.sync="drawer">
      <link-add @getLinkList="getLinkList" @closeAddDrawer="closeAddDrawer" />
    </el-drawer>

    <!-- 修改弹窗 -->
    <el-dialog title="修改" :visible.sync="updateDialog">
      <link-update :link="link" @getLinkList="getLinkList" @closeUpdateDialog="closeUpdateDialog" />
    </el-dialog>
  </div>
</template>

<script>
import linkApi from '@/api/link'
import LinkAdd from './link-add'
import LinkUpdate from './link-update'
export default {
  components: {
    LinkAdd,
    LinkUpdate
  },
  data() {
    return {
      link: {}, // 分类
      updateDialog: false, // 控制修改弹窗展示
      drawer: false, // 控制添加抽屉展示
      linkList: [] // 类型数组
    }
  },
  created() {
    this.getLinkList()
  },
  methods: {
    getLinkList() {
      // 查询类型列表
      linkApi.getList().then(res => {
        this.linkList = res.data
      })
    },
    handleEdit(id) {
      // 修改
      linkApi.get(id).then(res => {
        this.link = res.data
        this.updateDialog = true
      })
    },
    handleDelete(id) {
      // 删除
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        link: 'error'
      }).then(() => {
        linkApi.deleteById(id).then(res => {
          this.$message.success(res.msg)
          this.getLinkList()
        })
      })
    },
    openAddDrawer() {
      // 打开添加弹窗
      this.drawer = true
    },
    closeAddDrawer() {
      // 关闭添加抽屉
      this.drawer = false
    },
    closeUpdateDialog() {
      // 关闭修改弹窗
      this.updateDialog = false
    }
  }
}
</script>
