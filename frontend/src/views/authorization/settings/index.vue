<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="应用名称" v-model="queryParam.appName" style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select style="width: 160px;" v-model="queryParam.appCode" class="filter-item" placeholder="应用编码">
        <el-option v-for="item in platformInfo" :key="item.branchCode" :label="item.platformName" :value="item.branchCode"/>
      </el-select>
      <el-input placeholder="应用ip" v-model="queryParam.ip" style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-date-picker placeholder="有效时间" v-model="queryParam.validDate" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" class="filter-item"/>
      <el-button  class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">新增</el-button>
    </div>


    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row>
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ (scope.$index+1) }}
        </template>
      </el-table-column>
      <el-table-column label="应用名称"  align="center">
        <template slot-scope="scope">
          {{ scope.row.appName }}
        </template>
      </el-table-column>
      <el-table-column label="应用编码" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.appCode }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应用key" align="center">
        <template slot-scope="scope">
          {{ scope.row.appKey }}
        </template>
      </el-table-column>
      <el-table-column label="应用Secret" align="center">
        <template slot-scope="scope">
          {{ scope.row.appSecret }}
        </template>
      </el-table-column>
      <el-table-column label="应用ip地址" width="130" align="center">
        <template slot-scope="scope">
          {{ scope.row.ip }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="有效时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span>{{ scope.row.validDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status| statusNameFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status=='1'" type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="scope.row.status=='1'" size="mini" type="success" @click="handleUpdateKey(scope.row)">更新密钥</el-button>
          <el-button v-if="scope.row.status=='1'" size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          <el-button v-if="scope.row.status=='0'" size="mini" type="danger" @click="handleRecover(scope.row)">恢复</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParam.pageNo" :limit.sync="queryParam.pageSize" @pagination="fetchData" />

    <el-dialog :title="dialogTitleMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm"  :model="temp" label-position="left" label-width="90px" style="width: 400px; margin-left:50px;">
        <el-form-item label="应用名称" prop="appName">
          <el-input v-model="temp.appName" placeholder="请输入应用名称"/>
        </el-form-item>
        <el-form-item label="应用ip" prop="ip">
          <el-input v-model="temp.ip" placeholder="请输入IP地址"/>
        </el-form-item>
        <el-form-item label="应用code" prop="appCode" >
          <el-select style="width: 160px;" v-model="temp.appCode" class="filter-item" placeholder="应用编码">
            <el-option v-for="item in platformInfo" :key="item.branchCode" :label="item.platformName" :value="item.branchCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="有效时间" prop="validDate">
          <el-date-picker v-model="temp.validDate" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择时间"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">确定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { queryPage,saveOrUpdate,deleteOne,updateSecretKey,recover } from '@/api/authorization.js'
import { getPlatformBranchInfo } from '@/api/platform.js'
import Pagination from '@/components/Pagination'

export default {
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        2: 'gray',
        0: 'danger'
      }
      return statusMap[status]
    },
    statusNameFilter(status){
      const statusMap = {
        1: '有效',
        2: '已过时',
        0: '已删除'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      listLoading: true,
      total:0,
      platformInfo:[],
      queryParam: {
        appName:'',
        appCode:'',
        ip:'',
        validDate:'',
        pageSize:10,
        pageNo:1
      },
      temp:{
        id:undefined,
        appName:'',
        appCode:'',
        ip:'',
        validDate:''
      },
      dialogFormVisible: false,
      dialogStatus:'',
      dialogTitleMap:{
        create:'新增',
        update:'编辑'
      }

    }
  },
  created() {
    this.fetchAppCode()
    this.fetchData()
  },
  methods: {
    fetchAppCode(){
      getPlatformBranchInfo().then(response=> {
        let _platformInfo = response.data
        _platformInfo.unshift({platformName:'全部',branchCode:''})
        this.platformInfo = _platformInfo
      })
    },
    fetchData() {
      this.listLoading = true
      queryPage(this.queryParam).then(response => {
        this.list = response.data.rows
        this.listLoading = false
        this.total = response.data.total
      })
    },
    handleSizeChange(val) {
      this.queryParam.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryParam.pageNo = val
      this.fetchData()
    },
    handleFilter(){
      this.queryParam.pageNo = 1
      this.fetchData()
    },
    resetTemp() {
      this.temp = {
        id:undefined,
        appName:'',
        appCode:'',
        ip:'',
        validDate:''
      }
    },
    handleCreate(){
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleUpdate(row){
      this.temp.id = row.id
      this.temp.appName = row.appName
      this.temp.appCode = row.appCode
      this.temp.ip = row.ip
      this.temp.validDate = row.validDate
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    createData(){
      saveOrUpdate(this.temp).then(()=>{
        this.fetchData()
        this.dialogFormVisible = false
        this.$notify({
          title: '成功',
          message: '新增成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    updateData(){
      saveOrUpdate(this.temp).then(()=>{
        this.fetchData()
        this.dialogFormVisible = false
        this.$notify({
          title: '成功',
          message: '更新成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    handleUpdateKey(row){
      updateSecretKey(row.id).then(()=>{
        this.fetchData()
      })
    },
    handleDelete(row){
      deleteOne(row.id).then(()=>{
        this.fetchData()
      })
    },
    handleRecover(row){
      recover(row.id).then(()=>{
        this.fetchData()
      })
    }
  }
}
</script>
