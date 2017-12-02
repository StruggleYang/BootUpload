<p align="center">
    <img  src="http://oqr3htxnb.bkt.clouddn.com/17-12-2/61918887.jpg">
</p>
<h1 align="center">SpringBoot File Upload</h1>


### Quick Start

clone this repositories

```
git clone this repositories
```

Modify the uploaded path according to your OS by application.yml

```
run BootFileUploadApplication class
```

open your Browser

```
http://localhost:8089
```

>Auto redirecting to the swagger2 page

Start uploading

## In your project

You need two documents to support

- `org.struy.util.Tools`
- `org.struy.web.AccessoryController`

>You may have a little modification that can be used in your project



## Features
- `application.yml` Configuration upload path
- `h2` database Storage file info
- The file is stored at the `FileRoot/type/year/month/file` level to facilitate the maintenance of the file
- File downloading
- Picture/pdf/txt Preview

## Next
- Cloud storage scheme

## Contact
- Blog:[http://www.struy.top](http://www.struy.top)