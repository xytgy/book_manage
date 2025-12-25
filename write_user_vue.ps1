$path = 'E:\software\book-manage\bppk-manage-1\src\views\user\index.vue'
$content = @"
<template>
  <div class="user-container">
    <!-- 查询表单 -->
    <el-card class="filter-card">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input v-model="queryParams.username" placeholder="请输入用户名" clearable />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="queryParams.role" placeholder="请选择角色" clearable style="width: 180px">
            <el-option label="管理员" value="admin" />
            <el-option label="普通用户" value="user" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">
            <el-icon><Search /></el-icon> 查询
          </el-button>
          <el-button @click="resetQuery">
            <el-icon><Refresh /></el-icon> 重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮 -->
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增用户
          </el-button>
        </div>
      </template>

      <!-- 用户表格 -->
      <el-table :data="filteredUsers" border style="width: 100%" v-loading="loading">
        <el-table-column type="index" label="序号" width="60" align="center" />
        <el-table-column prop="username" label="用户名" align="center" />
        <el-table-column prop="nickname" label="昵称" align="center" />
        <el-table-column prop="role" label="角色" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.role === 'admin' ? 'danger' : 'success'">
              {{ scope.row.role === 'admin' ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" align="center">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" />
        <el-table-column label="操作" width="150" align="center">
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑对话框-->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑用户' : '新增用户'"
      width="500px"
      @closed="resetForm"
    >
      <el-form :model="userForm" :rules="rules" ref="userFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" placeholder="请输入用户名" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="userForm.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" placeholder="请选择角色" style="width: 100%">
            <el-option label="管理员" value="admin" />
            <el-option label="普通用户" value="user" />
          </el-select>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!isEdit">
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { Search, Refresh, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { getUserList, addUser, updateUser, deleteUser, updateUserStatus } from '@/api/user'
import type { User } from '@/api/user'

// 查询参数
const queryParams = reactive({
  username: '',
  role: ''
})

// 用户列表数据
const userList = ref<User[]>([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const userFormRef = ref<FormInstance>()

// 获取列表数据
const getList = async () => {
  loading.value = true
  try {
    const res: any = await getUserList(queryParams)
    userList.value = res.data
  } catch (error) {
    console.error('获取用户列表失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getList()
})

// 表单数据
const userForm = reactive({
  id: undefined as number | undefined,
  username: '',
  nickname: '',
  role: 'user',
  password: '',
  status: 1
})

// 表单校验规则
const rules = reactive<FormRules>({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

// 过滤后的用户列表 (如果有后端接口，通常由后端过滤)
const filteredUsers = computed(() => {
  if (!Array.isArray(userList.value)) return []
  return userList.value.filter(user => {
    const username = user.username || ""
    const queryUsername = queryParams.username || ""
    const matchUsername = username.toLowerCase().includes(queryUsername.toLowerCase())
    const matchRole = queryParams.role === "" || user.role === queryParams.role
    return matchUsername && matchRole
  })
})

// 查询和重置
const handleQuery = () => {
  getList()
}

const resetQuery = () => {
  queryParams.username = ''
  queryParams.role = ''
  handleQuery()
}

// 新增和编辑
const handleAdd = () => {
  isEdit.value = false
  dialogVisible.value = true
}

const handleEdit = (row: User) => {
  isEdit.value = true
  dialogVisible.value = true
  // 使用 nextTick 确保对话框已渲染，或者直接赋值
  Object.assign(userForm, {
    id: row.id,
    username: row.username,
    nickname: row.nickname,
    role: row.role,
    status: row.status,
    password: '' // 编辑时不显示密码
  })
}

const handleDelete = (row: User) => {
  if (!row.id) return
  ElMessageBox.confirm(`确定要删除用户"${row.nickname}" 吗？`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteUser(row.id!)
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      console.error('删除失败:', error)
    }
  })
}

const handleStatusChange = async (row: User) => {
  if (!row.id) return
  try {
    await updateUserStatus(row.id, row.status)
    ElMessage.success(`${row.username} 的状态已更新`)
  } catch (error) {
    row.status = row.status === 1 ? 0 : 1 // 恢复状态
  }
}

const resetForm = () => {
  userFormRef.value?.resetFields()
  Object.assign(userForm, {
    id: undefined,
    username: '',
    nickname: '',
    role: 'user',
    password: '',
    status: 1
  })
}

const submitForm = async () => {
  if (!userFormRef.value) return
  await userFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          if (userForm.id) {
            await updateUser(userForm.id, userForm as any)
            ElMessage.success('更新成功')
          }
        } else {
          await addUser(userForm as any)
          ElMessage.success('新增成功')
        }
        dialogVisible.value = false
        getList()
      } catch (error) {
        console.error('提交失败:', error)
      }
    }
  })
}
</script>

<style scoped>
.user-container {
  padding: 20px;
}

.filter-card {
  margin-bottom: 20px;
}

.table-card {
  min-height: 500px;
}

.card-header {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.demo-form-inline .el-form-item {
  margin-right: 20px;
}
</style>
"@
Set-Content -Path $path -Value $content -Encoding UTF8
