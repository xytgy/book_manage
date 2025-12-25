$path = 'E:\software\book-manage\bppk-manage-1\src\views\user\index.vue'
$content = Get-Content $path -Raw -Encoding UTF8

# Replace problematic characters using regex
$content = $content -replace '用户\?', '用户名'

Set-Content $path $content -Encoding UTF8