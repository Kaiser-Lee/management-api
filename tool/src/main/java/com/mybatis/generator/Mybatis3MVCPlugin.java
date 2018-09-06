package com.mybatis.generator;

import com.management.xdao.IMybatisDao;
import com.management.xservice.BaseService;
import org.apache.log4j.Logger;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;





public class Mybatis3MVCPlugin extends PluginAdapter{
	
	private String controllerTargetProject;
	private String controllerPackage;
	private String providerTargetProject;
	private String providerPackage;
	private String providerImplTargetProject;
	private String providerImplPackage;
	private String serviceTargetProject;
	private String servicePackage;
	private String serviceImplTargetProject;
	private String serviceImplPackage;
	
	private FullyQualifiedJavaType autowired;
	
	private FullyQualifiedJavaType controllerRootClass;
	
	private FullyQualifiedJavaType providerInterface;
	private FullyQualifiedJavaType providerRootInterface;
	private FullyQualifiedJavaType providerImplRootClass;
	
	private FullyQualifiedJavaType serviceInterface;
	private FullyQualifiedJavaType serviceRootInterface;
	private FullyQualifiedJavaType serviceImplRootClass;
	
	private FullyQualifiedJavaType daoInterface;
	private FullyQualifiedJavaType entityClass;

	@Override
	public boolean validate(List<String> arg0) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.PluginAdapter#contextGenerateAdditionalJavaFiles(org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		
		autowired = new FullyQualifiedJavaType(Autowired.class.getName());
		daoInterface = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		entityClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		
		serviceTargetProject = properties.getProperty("serviceTargetProject");
		servicePackage = properties.getProperty("servicePackage");
		serviceRootInterface = new FullyQualifiedJavaType(properties.getProperty("serviceRootInterface"));
		
		serviceImplTargetProject = properties.getProperty("serviceImplTargetProject");
		serviceImplPackage = properties.getProperty("serviceImplPackage");
		serviceImplRootClass = new FullyQualifiedJavaType(properties.getProperty("serviceImplRootClass"));
		
		
		List<GeneratedJavaFile> gjfs = new ArrayList<GeneratedJavaFile>();
		
		gjfs.add(generateService());
		gjfs.add(generateServiceImpl());
		
		controllerTargetProject = properties.getProperty("controllerTargetProject");
		controllerPackage = properties.getProperty("controllerPackage");
		String controllerRootClassProperty = properties.getProperty("controllerRootClass");
		
		if(!StringUtils.isEmpty(controllerPackage) && !StringUtils.isEmpty(controllerPackage)){
			controllerRootClass = new FullyQualifiedJavaType(controllerRootClassProperty);
			gjfs.add(generateController());
		}
		return gjfs;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateService() {
		
		serviceInterface = new FullyQualifiedJavaType(servicePackage + "."
				+ entityClass.getShortName() + "Service");
		
		Interface service = new Interface(serviceInterface);
		service.setVisibility(JavaVisibility.PUBLIC);
		service.addSuperInterface(serviceRootInterface);
		service.addImportedType(serviceRootInterface);
	
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				service,
				serviceTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateServiceImpl() {
		
		FullyQualifiedJavaType serviceImplInterface = new FullyQualifiedJavaType(
				serviceImplPackage + "." + entityClass.getShortName() + "ServiceImpl");
		
		TopLevelClass topLevelClass = new TopLevelClass(serviceImplInterface);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(serviceImplRootClass);
		topLevelClass.addSuperInterface(serviceInterface);
		topLevelClass.addAnnotation("@Service");
		
		//generateLoggerField(topLevelClass);
		
		String fieldName = StringUtils.uncapitalize(daoInterface.getShortName());
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(daoInterface);
		field.setName(fieldName);
		topLevelClass.addField(field);
		
		FullyQualifiedJavaType iMybatisDao = new FullyQualifiedJavaType(IMybatisDao.class.getName()+"<"+entityClass.getShortName()+">");
		Method method = new Method();
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("getBaseDao");
		method.setReturnType(iMybatisDao);
		method.addBodyLine("return " + fieldName + ";");
		topLevelClass.addMethod(method);
		
		topLevelClass.addImportedType(serviceImplRootClass);
		topLevelClass.addImportedType(serviceInterface);
		topLevelClass.addImportedType(autowired);
		topLevelClass.addImportedType(daoInterface);
		topLevelClass.addImportedType(iMybatisDao);
		topLevelClass.addImportedType(new FullyQualifiedJavaType(Service.class.getName()));
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				topLevelClass,
				serviceImplTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}
	
	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateProvider() {
		
		providerInterface = new FullyQualifiedJavaType(providerPackage + "."
				+ entityClass.getShortName() + "Provider");
		
		Interface provider = new Interface(providerInterface);
		provider.setVisibility(JavaVisibility.PUBLIC);
		provider.addSuperInterface(providerRootInterface);
		provider.addImportedType(providerRootInterface);
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				provider,
				providerTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateProviderImpl() {
		
		FullyQualifiedJavaType providerImplInterface = new FullyQualifiedJavaType(
				providerImplPackage + "." + entityClass.getShortName() + "ProviderImpl");
		
		TopLevelClass topLevelClass = new TopLevelClass(providerImplInterface);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(providerImplRootClass);
		topLevelClass.addSuperInterface(providerInterface);
		topLevelClass.addAnnotation("@Service");
		
		generateLoggerField(topLevelClass);
		
		String fieldName = StringUtils.uncapitalize(serviceInterface.getShortName());
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(serviceInterface);
		field.setName(fieldName);
		topLevelClass.addField(field);
		
		FullyQualifiedJavaType iBaseService = new FullyQualifiedJavaType(BaseService.class.getName()+"<"+entityClass.getShortName()+">");
		Method method = new Method();
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("getBaseService");
		method.setReturnType(iBaseService);
		method.addBodyLine("return " + fieldName + ";");
		topLevelClass.addMethod(method);
		
		topLevelClass.addImportedType(providerImplRootClass);
		topLevelClass.addImportedType(providerInterface);
		topLevelClass.addImportedType(autowired);
		topLevelClass.addImportedType(serviceInterface);
		topLevelClass.addImportedType(iBaseService);
		topLevelClass.addImportedType(new FullyQualifiedJavaType(Service.class.getName()));
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				topLevelClass,
				providerImplTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateController() {
		
		FullyQualifiedJavaType controller = new FullyQualifiedJavaType(
				controllerPackage + "." + entityClass.getShortName() + "Controller");
		
		TopLevelClass topLevelClass = new TopLevelClass(controller);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(controllerRootClass);
		topLevelClass.addAnnotation("@Controller");
		//topLevelClass.addAnnotation(String.format("@RequestMapping(\"%s\")", StringUtils.uncapitalize(entityClass.getShortName())));
		
		//generateLoggerField(topLevelClass);
		
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(serviceInterface);
		field.setName(StringUtils.uncapitalize(serviceInterface.getShortName()));
		topLevelClass.addField(field);
		
		topLevelClass.addImportedType(new FullyQualifiedJavaType(Controller.class.getName()));
		topLevelClass.addImportedType(new FullyQualifiedJavaType(RequestMapping.class.getName()));
		topLevelClass.addImportedType(controllerRootClass);
		topLevelClass.addImportedType(autowired);
		topLevelClass.addImportedType(serviceInterface);
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				topLevelClass,
				controllerTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}
	
	private void generateLoggerField(TopLevelClass topLevelClass){
		
		FullyQualifiedJavaType log4jLogger = new FullyQualifiedJavaType(Logger.class.getName());
		
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setStatic(true);
		field.setFinal(true);
		field.setType(log4jLogger);
		field.setName("logger");
		field.setInitializationString(String.format(
				"Logger.getLogger(%s.class)", topLevelClass.getType()
						.getShortName()));
		
		topLevelClass.addField(field);
		topLevelClass.addImportedType(log4jLogger);
	}
	
	
}
