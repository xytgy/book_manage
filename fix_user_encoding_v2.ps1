$path = 'E:\software\book-manage\bppk-manage-1\src\views\user\index.vue'
$content = Get-Content $path -Raw -Encoding UTF8

# Individual character replacements
$content = $content -replace '用户\?', '用户名'
$content = $content -replace '管理\?', '管理员'
$content = $content -replace '普通用\?', '普通用户'
$content = $content -replace '昵\?', '昵称'
$content = $content -replace '状\?', '状态'
$content = $content -replace '密\?', '密码'

# Fix the rules object syntax
$oldRules = @"
// 表单校验规则
const rules = reactive<FormRules>({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵?, trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
  password: [{ required: true, message: '请输入密?, trigger: 'blur' }]
})
"@

$newRules = @"
// 表单校验规则
const rules = reactive<FormRules>({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})
"@

$content = $content -replace [regex]::Escape($oldRules), $newRules

Set-Content $path $content -Encoding UTF8