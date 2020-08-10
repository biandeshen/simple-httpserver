使用 serverSocketChannel 与 SocketChannel 等 NIO 包的类实现多路复用的 http 服务  
https://www.cnblogs.com/lilei2blog/p/9371469.html


1. 如果服务器知道该方法但不允许所请求的资源，则服务器应返回状态码405（不允许的方法），如果服务器无法识别或未实现该方法，则服务器应返回状态代码501（未实现）。 TODO

此次较socket实现多支持了Content-Type为multipart/form-data以及application/x-www-form-urlencoded的请求