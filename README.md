<p align="center">
    <img  src="http://oqr3htxnb.bkt.clouddn.com/17-12-26/37826608.jpg">
</p>
<h1 align="center">SpringBoot File Upload</h1>


### Quick Start

clone this repositories

```
git clone this repositories
```

Modify the uploaded path according to your OS by application.yml

> If you're trying to upload a qiniu cloud, you need to modify the corresponding key

```
run UploadApplication class
```

open your Browser

```
http://localhost:8089
```

>Auto redirecting to the swagger2 page

Start uploading

## In your project

You need two documents to support

- `org.struy.util.*`
- `org.struy.web.AccessoryController`

>You may have a little modification that can be used in your project



## Features
- `application.yml` Configuration upload path
- `h2` database Storage file info
- The file is stored at the `FileRoot/type/year/month/file` level to facilitate the maintenance of the file
- File downloading
- Picture/pdf/txt Preview
- Cloud storage scheme by [qiniu](https://www.qiniu.com)

#### upload in qiniu cloud
<p align="center">
    <img  src="http://oqr3htxnb.bkt.clouddn.com/2017-12-31/3f1915210ae6447dbba0e2ec2f4543d0.png">
</p>

## Next
- Still want to

## Contact
- Blog:[http://struy.cn](http://struy.cn)
