一、自定义认证处理器
	1、找到WEB-INF下deployerConfigContext.xml
	2、注释掉默认的认证处理器primaryAuthenticationHandler，在authenticationManager中为primaryPrincipalResolver指定自定义的认证处理器dbAuthHandler
	3、创建认证处理器dbAuthHandler，指定datasourse、passwordEncoder、sql

二、自定义客户端用户数据返回
	1、注释掉原有的attributeRepository
	2、自定义新的attributeRepository，实现类AccoutAttributeDao
	3、在registeredServicesList中增加属性allowedAttributes，以list方式增加返回的属性名称
	4、在protocol 2.0文件下 的 casServiceValidationSuccess.jsp页面加上 以下代码：
	<c:if test="${fn:length(assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.attributes) > 0}">  
        <cas:attributes>  
            <c:forEach var="attr" items="${assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.attributes}">  
                <cas:${fn:escapeXml(attr.key)}>${fn:escapeXml(attr.value)}</cas:${fn:escapeXml(attr.key)}>  
            </c:forEach>  
        </cas:attributes>  
    </c:if>  