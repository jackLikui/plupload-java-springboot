var uploader = new plupload.Uploader({
    browse_button: 'file-picker',
    url: '/uploadProject/upload',
    chunk_size: '1mb'
});

uploader.init();

function getOffset(files) {
    return new Promise(function(resolve, reject) {
        var xhr = new XMLHttpRequest();
        xhr.onload = function(d) {
            var percent = parseInt(d.currentTarget.response);
            for(var i =0; i<files.length; i++) {
                console.log(files[i]);
                files[i].percent = percent;
                files[i].loaded = parseInt(percent*files[i].size/100);
            }
            resolve(percent);
        };
        calculate(files[0].getNative()).then(function (md5) {
            var name = files[0].getNative().name;
            var formData = new FormData();
            formData.append("md5",md5);
            formData.append("name",name);
            xhr.open('post', '/uploadProject/offset');
            xhr.send(formData);
        })
    });
}


uploader.bind('FilesAdded', function(up, files) {
    var ulRef = document.getElementById('filelist');
    plupload.each(files, function(file) {
        var liRef = document.createElement("li");
        liRef.innerText = 'Step1: select file ' + file.name;
        ulRef.appendChild(liRef);

        var liRef = document.createElement("li");
        liRef.setAttribute('id', file.id+'tip');
        ulRef.appendChild(liRef);
        var liRef = document.createElement("li");
        liRef.setAttribute('id', file.id+'progress');
        ulRef.appendChild(liRef);
    });
    var promiseArr = [];
    getOffset(files)
        .then(function(percent) {
            plupload.each(files, function(file) {
                document.getElementById(file.id+'tip')
                    .innerText = 'Step2: get a random break-point from server: ' + percent + '%';
                promiseArr.push(calculate(file.getNative()))
                //     .then(function (md5) {
                //     uploader.setOption("multipart_params",[{"md5":md5}])
                // })
            });
        }).then(function () {
            Promise.all(promiseArr)
        .then(function(result) {
            [m] = result;
            var param = {}
            param.md5=m
            uploader.setOption("multipart_params",param)
            up.start();
        }); })
});
uploader.bind('UploadProgress', function(up, file) {
    document.getElementById(file.id+'progress')
        .innerText = 'Step3: upload ' + file.percent + "%";
});

function calculate(file){
    return new Promise(function (resolve, reject) {
        var fileReader = new FileReader(),
            box=document.getElementById('box');
        blobSlice = File.prototype.mozSlice || File.prototype.webkitSlice || File.prototype.slice,
            chunkSize = 209715200,
            // read in chunks of 2MB
            chunks = Math.ceil(file.size / chunkSize),
            currentChunk = 0,
            spark = new SparkMD5();

        fileReader.onload = function(e) {
            console.log("read chunk nr", currentChunk + 1, "of", chunks);
            spark.appendBinary(e.target.result); // append binary string
            currentChunk++;

            if (currentChunk < chunks) {
                loadNext();
            }
            else {
                console.log("finished loading");
                var md5Str = spark.end();
                box.innerText='MD5 hash:'+md5Str; // compute hash
                console.log(md5Str)
                resolve(md5Str)
            }
        };

        function loadNext() {
            var start = currentChunk * chunkSize,
                end = start + chunkSize >= file.size ? file.size : start + chunkSize;
            fileReader.readAsBinaryString(blobSlice.call(file, start, end));
        };
        loadNext();
    })
}