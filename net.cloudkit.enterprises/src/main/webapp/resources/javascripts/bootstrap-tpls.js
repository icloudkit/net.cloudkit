// $templateCache.get('templateId.html');
// $http, $templateCache

angular.module("bootstrap.tpls", ["resources/templates/common/header.html", "resources/templates/partials/disklist.html"]);


angular.module("resources/templates/common/header.html", []).run([ "$templateCache", function($templateCache) {
	"use strict";
	$templateCache.put("resources/templates/common/header.html",
		"<div id=\"header\">\n"+
		"<nav id=\"top-link\" class=\"navbar navbar-default\">\n"+
		"    <!-- container-fluid -->\n"+
		"    <div class=\"container\">\n"+
		"        <ul class=\"nav navbar-nav\">\n"+
		"            <li class=\"dropdown\">\n"+
		"                <a href=\"javascript:void(0);\" class=\"mobile-dropdown dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">手机客户端 <span class=\"caret\"></span></a>\n"+
		"                <ul class=\"dropdown-menu\">\n"+
		"                    <li><a href=\"javascript:void(0);\">Action</a></li>\n"+
		"                    <li><a href=\"javascript:void(0);\">Another action</a></li>\n"+
		"                    <li><a href=\"javascript:void(0);\">Something else here</a></li>\n"+
		"                    <li role=\"separator\" class=\"divider\"></li>\n"+
		"                    <li><a href=\"javascript:void(0);\">Separated link</a></li>\n"+
		"                    <li role=\"separator\" class=\"divider\"></li>\n"+
		"                    <li><a href=\"javascript:void(0);\">One more separated link</a></li>\n"+
		"                </ul>\n"+
		"            </li>\n"+
		"        </ul>\n"+
		"        <ul class=\"nav navbar-nav navbar-right\">\n"+
		"            <li class=\"dropdown\">\n"+
		"                <a href=\"javascript:void(0);\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">您好，Coffee <span class=\"caret\"></span></a>\n"+
		"                <ul class=\"dropdown-menu\">\n"+
		"                    <li><a href=\"javascript:void(0);\">Action</a></li>\n"+
		"                    <li><a href=\"javascript:void(0);\">Another action</a></li>\n"+
		"                    <li><a href=\"javascript:void(0);\">Something else here</a></li>\n"+
		"                    <li role=\"separator\" class=\"divider\"></li>\n"+
		"                    <li><a href=\"javascript:void(0);\">Separated link</a></li>\n"+
		"                </ul>\n"+
		"            </li>\n"+
		"            <li><a href=\"javascript:void(0);\">退出</a></li>\n"+
		"            <li class=\"spliter\"></li>\n"+
		"            <li><a class=\"help-link\" href=\"javascript:void(0);\">帮助中心</a></li>\n"+
		"        </ul>\n"+
		"    </div>\n"+
		"    <!-- /.container-fluid -->\n"+
		"</nav>\n"+
		"\n"+
		"<div id=\"header-navbar\" class=\"navbar navbar-inverse\">\n"+
		"    <!-- container-fluid -->\n"+
		"    <div class=\"container\">\n"+
		"        <!-- Brand -->\n"+
		"        <div class=\"navbar-header\">\n"+
		"            <a class=\"navbar-brand\" href=\"javascript:void(0);\">\n"+
		"                <!-- Generator: Adobe Illustrator 15.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n"+
		"                <svg version=\"1.1\" id=\"logo\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"158px\" height=\"36px\" viewBox=\"0 0 158 36\" enable-background=\"new 0 0 158 36\" xml:space=\"preserve\">\n"+
		"                <g>\n"+
		"                    <path fill=\"#ffffff\" d=\"M10.624,14.719C4.766,14.719,0,19.488,0,25.344c0,5.862,4.766,10.623,10.624,10.623c5.859,0,10.624-4.761,10.624-10.623C21.248,19.488,16.483,14.719,10.624,14.719z M10.624,35.201c-5.445,0-9.858-4.414-9.858-9.857c0-5.442,4.413-9.858,9.858-9.858c5.444,0,9.859,4.416,9.859,9.858C20.483,30.787,16.068,35.201,10.624,35.201z\"/>\n"+
		"                    <path fill=\"#ffffff\" d=\"M35.339,14.719c-5.858,0-10.625,4.77-10.625,10.625c0,5.862,4.767,10.623,10.625,10.623c5.858,0,10.623-4.761,10.623-10.623C45.962,19.488,41.197,14.719,35.339,14.719z M35.339,35.201c-5.447,0-9.859-4.414-9.859-9.857c0-5.442,4.412-9.858,9.859-9.858c5.445,0,9.857,4.416,9.857,9.858C45.196,30.787,40.784,35.201,35.339,35.201z\"/>\n"+
		"                    <path fill=\"#ffffff\" d=\"M45.328,5.942c-7.195,0-13.05,5.856-13.05,13.056c0,7.197,5.855,13.054,13.05,13.054c7.201,0,13.059-5.856,13.059-13.054C58.387,11.798,52.529,5.942,45.328,5.942z M45.328,31.287c-6.785,0-12.286-5.502-12.286-12.288c0-6.79,5.501-12.292,12.286-12.292c6.794,0,12.295,5.501,12.295,12.292C57.624,25.784,52.12,31.287,45.328,31.287z\"/>\n"+
		"                    <path fill=\"#ffffff\" d=\"M23.068,0C14.343,0,7.245,7.098,7.245,15.824c0,8.726,7.098,15.827,15.824,15.827c8.724,0,15.823-7.101,15.823-15.827C38.891,7.098,31.792,0,23.068,0z M23.068,30.88c-8.317,0-15.059-6.74-15.059-15.056c0-8.317,6.742-15.059,15.059-15.059c8.317,0,15.056,6.742,15.056,15.059C38.125,24.14,31.386,30.88,23.068,30.88z\"/>\n"+
		"                    <text transform=\"matrix(1 0 0 1 63.5 26.3428)\" fill=\"#ffffff\" font-family=\"'Microsoft YaHei'\" font-size=\"24\">云景</text>\n"+
		"                </g>\n"+
		"                </svg>\n"+
		"            </a>\n"+
		"        </div>\n"+
		"        <ul class=\"nav navbar-nav navbar-right\">\n"+
		"            <li><a href=\"javascript:void(0);\">首页</a></li>\n"+
		"            <li><a href=\"javascript:void(0);\">账户管理</a></li>\n"+
		"            <li><a class=\"service-collection\" href=\"javascript:void(0);\">应用中心</a></li>\n"+
		"        </ul>\n"+
		"    </div>\n"+
		"    <!-- /.container-fluid -->\n"+
		"</div>\n"+
	    "</div>");
} ]);

angular.module("resources/templates/partials/disklist.html", []).run([ "$templateCache", function($templateCache) {
	"use strict";
	$templateCache.put("resources/templates/partials/disklist.html", 
		"<h4>$templateCache</h4>" + 
		"<p>这是直接调用$templateCache服务获取模板文件的方式</p>" + 
		"<a href=\"http://www.baidu.com\">服务启用templateCache方式</a>");
}]);