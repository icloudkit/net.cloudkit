window._browserIsNotSupported = true;
if (window.attachEvent) {
	window.attachEvent('onload', function() {
		var element = document.createElement('div'), elementStyle = element.style, body = document.getElementsByTagName('body')[0], linkStyle = 'color:#06F;text-decoration: underline;';
		element.innerHTML = '尊敬的用户：<br />' 
			+ '使用该应用需要安装Internet Explorer更新版本的浏览器，' 
			+ '请<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" style="' + linkStyle + '" target="_blank">下载安装IE8</a>（或更新）。' 
			+ '也可以在其他浏览器，' + '如<a href="http://www.google.com/intl/zh-CN/chrome/" style="' + linkStyle + '" target="_blank">Chrome</a>' 
			+ '或<a href="http://www.firefox.com.cn/download/" style="' + linkStyle + '" target="_blank">Firefox</a>火狐中打开。';
		// elementStyle.width = '100%';
		elementStyle.width = '720px';
		elementStyle.color = '#000';
		elementStyle.fontSize = '14px';
		elementStyle.lineHeight = '180%';
		elementStyle.margin = '0 auto';
		// elementStyle.backgroundColor = '#fffbd5';
		elementStyle.border = '1px solid #CCC';
		elementStyle.padding = '24px 48px';
		elementStyle.background = '#F00 url(images/not-support-ie67.png) 48px 48px no-repeat';
		elementStyle.padding = '40px 40px 48px 160px';
		body.innerHTML = '';
		body.appendChild(element);
		// body.insertBefore(element,body.firstChild);
	});
}