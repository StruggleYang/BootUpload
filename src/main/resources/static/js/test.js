var  files =  new $FileUpload("Test");
$(function () {
    files.init();
});

var Test = function () {
    var fileNames = files.successFileList;
    console.log(fileNames);
    var html = "";
    for(var i = 0;i<fileNames.length;i++){
        html += "["+fileNames[i]._raw+"]"
    }
    alert("上传了"+fileNames.length+"个文件："+html)
}