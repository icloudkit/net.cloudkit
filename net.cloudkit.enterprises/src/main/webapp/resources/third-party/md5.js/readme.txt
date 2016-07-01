MD5
RIPEMD-160
SHA-1
SHA-256
SHA-512
HMAC

http://pajhome.org.uk/crypt/md5/index.html

<script type="text/javascript">  
    var b = new Base64();
    var str = b.encode("admin:admin");
    alert("base64 encode:" + str);
    //解密
    str = b.decode(str);
    alert("base64 decode:" + str);

    var hash = hex_md5("123dafd");
    alert(hash)

    var sha = hex_sha1('mima123465')
    alert(sha)
</script>