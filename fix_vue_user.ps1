$path2 = 'E:\software\book-manage\bppk-manage-1\src\views\user\index.vue'
$content2 = Get-Content $path2 -Raw
$pattern2 = 'const filteredUsers = computed\(\(\) => \{[\s\S]*?return userList\.value\.filter\(user => \{[\s\S]*?const matchUsername = user\.username\.toLowerCase\(\)\.includes\(queryParams\.username\.toLowerCase\(\)\)[\s\S]*?const matchRole = queryParams\.role === '''' \|\| user\.role === queryParams\.role[\s\S]*?return matchUsername && matchRole[\s\S]*?\}\)[\s\S]*?\}\)'
$newStr2 = 'const filteredUsers = computed(() => {
  if (!Array.isArray(userList.value)) return []
  return userList.value.filter(user => {
    const username = user.username || ""
    const queryUsername = queryParams.username || ""
    const matchUsername = username.toLowerCase().includes(queryUsername.toLowerCase())
    const matchRole = queryParams.role === "" || user.role === queryParams.role
    return matchUsername && matchRole
  })
})'
$content2 = $content2 -replace $pattern2, $newStr2
Set-Content $path2 $content2
