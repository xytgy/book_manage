$path = 'E:\software\book-manage\bppk-manage-1\src\views\user\index.vue'
$content = Get-Content $path -Raw -Encoding UTF8

# Replace problematic characters
$content = $content -replace '用户\?', '\u7528\u6237\u540d'
$content = $content -replace '管理\?', '\u7ba1\u7406\u5458'
$content = $content -replace '普通用\?', '\u666e\u901a\u7528\u6237'
$content = $content -replace '昵\?', '\u6635\u79f0'
$content = $content -replace '状\?', '\u72b6\u6001'
$content = $content -replace '密\?', '\u5bc6\u7801'

# Fix the rules object syntax
$oldRules = @"
// 表单校验规则
const rules = reactive<FormRules>({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵?', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
  password: [{ required: true, message: '请输入密?', trigger: 'blur' }]
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