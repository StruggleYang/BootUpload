/**
 * 文件上传封装
 * 规约:eventId上传模块id
 *
 * html:
 *<div id="uploader" class="wu-example">
 *<!--用来存放文件信息-->
 *  <div id="weeklyFileList" class="uploader-list"></div>
 *  <div class="btns">
 *  <div id="weeklyPicker">选择文件</div>
 *  <button id="weeklyExecuteUploadBtn" class="btn btn-default">开始上传</button>
 *  </div>
 *</div>
 */
(function () {
    var $FileUpload = function (eventId) {
        this.pickId = eventId+'Picker';
        this.fileListId  = eventId+'FileList';
        this.ExecuteBtn = eventId+'ExecuteUploadBtn'; // 执行上传操作的按钮
        this.successFileList = []; // 上传成功后的文件名数组
    };
    $FileUpload.prototype = {
        /**
         * 上传文件初始化
         * @returns {*}
         */
        init:function () {
            var uploader = this.create();
            this.bindEvent(uploader);
            return uploader;
        },
        /**
         * 上传模块初始化
         */
        create:function () {
            var webUploader = WebUploader.create({

                // swf文件路径
                swf: 'puls/webuploader-0.1.5//Uploader.swf',

                // 文件接收服务端。
                server:'/upload',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#' + this.pickId,

                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,
            });
            return webUploader;
        },
        /**
         * 事件添加
         * @param obj
         */
        bindEvent:function (obj) {
            var me =  this;
            // 当有文件被添加进队列的时候
            obj.on( 'fileQueued', function( file ) {
                var $list = $('#'+me.fileListId);
                $list.append( '<div id="' + file.id + '" class="item">' +
                    '<h4 class="info">' + file.name + '</h4>' +
                    '<p class="state">等待上传,点击开始即上传...</p>' +
                    '</div>' );
            });

            // 文件上传过程中创建进度条实时显示。
            obj.on( 'uploadProgress', function( file, percentage ) {
                var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress .progress-bar');

                // 避免重复创建
                if ( !$percent.length ) {
                    $percent = $('<div class="progress progress-striped active">' +
                        '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                        '</div>' +
                        '</div>').appendTo( $li ).find('.progress-bar');
                }

                $li.find('p.state').text('上传中');

                $percent.css( 'width', percentage * 100 + '%' );
            });

            obj.on( 'uploadSuccess', function( file ) {
                $( '#'+file.id ).find('p.state').text('已上传');
            });

            obj.on( 'uploadError', function( file ) {
                $( '#'+file.id ).find('p.state').text('上传出错');
            });

            obj.on( 'uploadComplete', function( file ) {
                $( '#'+file.id ).find('.progress').fadeOut();
            });

            /**
             * 执行上传
             */
            $('#'+me.ExecuteBtn).click(function(){
                obj.upload();
                // 上传成功后将返回的文件名存储到上传成功的文件列表中
                obj.on('uploadAccept',function (object,ret) {
                    me.successFileList.push(ret);
                })
            });

        }
    };

    window.$FileUpload = $FileUpload;
}());


