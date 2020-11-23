
document.write('<script src="./js/vue-2.6.1.js" type="text/javascript"></script>');
document.write('<script src="lib/element-ui/index-2.13.0.js" type="text/javascript"></script>');
document.write('<script src="./js/jquery-3.4.1.min.js" type="text/javascript"></script>');
document.write('<script src="./js/dateUtils.js" type="text/javascript"></script>');
const HOST = '';

// 获得当前时间 2019-02-02 14:06:08
function getNowTime() {
    // 加0
    function add_10(num) {
        if (num < 10) {
            num = '0' + num
        }
        return num;
    }
    var myDate = new Date();
    myDate.getYear(); //获取当前年份(2位)
    myDate.getFullYear(); //获取完整的年份(4位,1970-????)
    myDate.getMonth(); //获取当前月份(0-11,0代表1月)
    myDate.getDate(); //获取当前日(1-31)
    myDate.getDay(); //获取当前星期X(0-6,0代表星期天)
    myDate.getTime(); //获取当前时间(从1970.1.1开始的毫秒数)
    myDate.getHours(); //获取当前小时数(0-23)
    myDate.getMinutes(); //获取当前分钟数(0-59)
    myDate.getSeconds(); //获取当前秒数(0-59)
    myDate.getMilliseconds(); //获取当前毫秒数(0-999)
    myDate.toLocaleDateString(); //获取当前日期
    var nowTime = myDate.getFullYear() + '-' + add_10(myDate.getMonth() + 1) + '-' + myDate.getDate() + ' ' + add_10(myDate.getHours()) + ':' + add_10(myDate.getMinutes()) + ':' + add_10(myDate.getSeconds());
    return nowTime;
}

function doPost(url,data) {
    var responseData;
    jQuery.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        dataType: "json",
        async: false,
        contentType: "application/json",
        success: function (data) {
            responseData = data;
        }, error: function (err) {
            console.log(err);
        }
    });
    return responseData;
}