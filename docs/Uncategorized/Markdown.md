针对中文,演示Markdown的各种语法

大标题
===================================
  大标题一般显示工程名,类似html的\<h1\><br />
  你只要在标题下面跟上=====即可


中标题
-----------------------------------
  中标题一般显示重点项,类似html的\<h2\><br />
  你只要在标题下面输入------即可

### 小标题
  小标题类似html的\<h3\><br />
  小标题的格式如下 ### 小标题<br />
  注意#和标题字符中间要有空格

### 注意!!!下面所有语法的提示我都先用小标题提醒了!!!

### 单行文本框
    这是一个单行的文本框,只要两个Tab再输入文字即可

### 多行文本框
    这是一个有多行的文本框
    你可以写入代码等,每行文字只要输入两个Tab再输入文字即可
    这里你可以输入一段代码

### 比如我们可以在多行文本框里输入一段代码,来一个Java版本的HelloWorld吧
    public class HelloWorld {

      /**
      * @param args
	    */
	    public static void main(String[] args) {
		    System.out.println("HelloWorld!");

	    }

    }
### 链接
1.[点击这里你可以链接到www.google.com](http://www.google.com)<br />
2.[点击这里我你可以链接到我的博客](http://blog.ticeng.com)<br />

###只是显示图片
![github](http://github.com/unicorn.png "github")

###想点击某个图片进入一个网页,比如我想点击github的icorn然后再进入www.github.com
[![image]](http://www.github.com/)
[image]: http://github.com/github.png "github"

### 文字被些字符包围
> 文字被些字符包围
>
> 只要再文字前面加上>空格即可
>
> 如果你要换行的话,新起一行,输入>空格即可,后面不接文字
> 但> 只能放在行首才有效

### 文字被些字符包围,多重包围
> 文字被些字符包围开始
>
> > 只要再文字前面加上>空格即可
>
>  > > 如果你要换行的话,新起一行,输入>空格即可,后面不接文字
>
> > > > 但> 只能放在行首才有效

### 特殊字符处理
有一些特殊字符如<,#等,只要在特殊字符前面加上转义字符\即可<br />
你想换行的话其实可以直接用html标签\<br /\>



* 在行首加点
行首输入*，空格后输入内容即可

........................................................................................................................


# Markdown 语法简明手册

### 1. 使用 * 和 ** 表示斜体和粗体

示例：

这是 *斜体*，这是 **粗体**。

### 2. 使用 === 表示一级标题，使用 --- 表示二级标题

示例：

这是一个一级标题
============================

这是一个二级标题
--------------------------------------------------

### 这是一个三级标题

你也可以选择在行首加井号表示不同级别的标题，例如：# H1, ## H2, ### H3。

### 3. 使用 \[描述](链接地址) 为文字增加外链接

示例：

这是去往 [本人博客](http://ghosertblog.github.com) 的链接。

### 4. 在行末加两个空格表示换行

示例：

第一行(此行最右有两个看不见的空格)
第二行

### 5. 使用 *，+，- 表示无序列表

示例：

- 无序列表项 一
- 无序列表项 二
- 无序列表项 三

### 6. 使用数字和点表示有序列表

示例：

1. 有序列表项 一
2. 有序列表项 二
3. 有序列表项 三

### 7. 使用 > 表示文字引用

示例：

> 野火烧不尽，春风吹又生

### 8. 使用 \`代码` 表示行内代码块

示例：

让我们聊聊 `html`

### 9.  使用 四个缩进空格 表示代码块

示例：

    这是一个代码块，此行左侧有四个不可见的空格

### 10.  使用 \!\[描述](图片链接地址) 插入图像

示例：

![我的头像](http://tp3.sinaimg.cn/2204681022/180/5606968568/1)

# Cmd 高阶语法手册

### 1. LaTeX 公式，表达式支持

$ 表示行内公式：

质能守恒方程可以用一个很简洁的方程式 $E=mc^2$ 来表达

$$ 表示整行公式：

$$\sum_{i=1}^n a_i=0$$

$$f(x_1,x_x,\ldots,x_n) = x_1^2 + x_2^2 + \cdots + x_n^2 $$

$$\sum^{j-1}_{k=0}{\widehat{\gamma}_{kj} z_k}$$

### 2. 加强的代码块，支持四十一种编程语言的语法高亮的显示，行号显示

非代码示例：

```
$ sudo apt-get install vim-gnome
```

Python 示例：

```python
@requires_authorization
def somefunc(param1='', param2=0):
    '''A docstring'''
    if param1 > param2: # interesting
        print 'Greater'
    return (param2 - param1 + 1) or None

class SomeClass:
    pass

>>> message = '''interpreter
... prompt'''
```

JavaScript 示例：

``` javascript
/**
* nth element in the fibonacci series.
* @param n >= 0
* @return the nth element, >= 0.
*/
function fib(n) {
  var a = 1, b = 1;
  var tmp;
  while (--n >= 0) {
    tmp = a;
    a += b;
    b = tmp;
  }
  return a;
}

document.write(fib(10));
```

### 3. 表格支持

示例：

| 项目        | 价格   |  数量  |
| --------   | -----:  | :----:  |
| 计算机     | $1600 |   5     |
| 手机        |   $12   |   12   |
| 管线        |    $1    |  234  |


### 4. 定义型列表

名词 1
:   定义 1（左侧有一个可见的冒号和四个不可见的空格）

代码块 2
:   这是代码块的定义（左侧有一个可见的冒号和四个不可见的空格）

        代码块（左侧有八个不可见的空格）


