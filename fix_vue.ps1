$path = 'E:\software\book-manage\bppk-manage-1\src\views\borrow\index.vue'
$content = Get-Content $path -Raw
$oldStr = 'const filteredRecords = computed(() => {
  return borrowRecords.value.filter(record => {
    const matchUser = record.username.toLowerCase().includes(queryParams.username.toLowerCase())
    const matchBook = record.bookTitle.toLowerCase().includes(queryParams.bookTitle.toLowerCase())
    const matchStatus = queryParams.status === undefined || record.status === queryParams.status
    return matchUser && matchBook && matchStatus
  })
})'
$newStr = 'const filteredRecords = computed(() => {
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
$content = $content.Replace($oldStr, $newStr)
Set-Content $path $content
