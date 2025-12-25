# Fix borrow/index.vue
$path1 = 'E:\software\book-manage\bppk-manage-1\src\views\borrow\index.vue'
$content1 = Get-Content $path1 -Raw
$oldStr1 = 'const filteredRecords = computed(() => {
  return borrowRecords.value.filter(record => {
    const matchUser = record.username.toLowerCase().includes(queryParams.username.toLowerCase())
    const matchBook = record.bookTitle.toLowerCase().includes(queryParams.bookTitle.toLowerCase())
    const matchStatus = queryParams.status === undefined || record.status === queryParams.status
    return matchUser && matchBook && matchStatus
  })
})'
$newStr1 = 'const filteredRecords = computed(() => {
  if (!Array.isArray(borrowRecords.value)) return []
  return borrowRecords.value.filter(record => {
    const username = record.username || ""
    const bookTitle = record.bookTitle || ""
    const queryUsername = queryParams.username || ""
    const queryBookTitle = queryParams.bookTitle || ""
    
    const matchUser = username.toLowerCase().includes(queryUsername.toLowerCase())
    const matchBook = bookTitle.toLowerCase().includes(queryBookTitle.toLowerCase())
    const matchStatus = queryParams.status === undefined || record.status === queryParams.status
    return matchUser && matchBook && matchStatus
  })
})'
if ($content1 -contains 'record.username.toLowerCase()') {
    $content1 = $content1.Replace($oldStr1, $newStr1)
    Set-Content $path1 $content1
}

# Fix user/index.vue
$path2 = 'E:\software\book-manage\bppk-manage-1\src\views\user\index.vue'
$content2 = Get-Content $path2 -Raw
$oldStr2 = 'const filteredUsers = computed(() => {
  return userList.value.filter(user => {
    const matchUsername = user.username.toLowerCase().includes(queryParams.username.toLowerCase())
    const matchRole = queryParams.role === '''' || user.role === queryParams.role
    return matchUsername && matchRole
  })
})'
$newStr2 = 'const filteredUsers = computed(() => {
  if (!Array.isArray(userList.value)) return []
  return userList.value.filter(user => {
    const username = user.username || ""
    const queryUsername = queryParams.username || ""
    const matchUsername = username.toLowerCase().includes(queryUsername.toLowerCase())
    const matchRole = queryParams.role === '''' || user.role === queryParams.role
    return matchUsername && matchRole
  })
})'
$content2 = $content2.Replace($oldStr2, $newStr2)
Set-Content $path2 $content2
