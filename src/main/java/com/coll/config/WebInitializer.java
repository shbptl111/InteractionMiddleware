package com.coll.config;

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//import com.fasterxml.classmate.Filter;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("---------------getRootConfig Classes----------------");
		return new Class[] { WebResolver.class, DBConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("---------------------getServletConfigClass---------------------");
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("---------------------------getServletMappings--------------------");
		return new String[] { "/" };
	}

	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(StandardCharsets.UTF_8.name());
		return new Filter[] { encodingFilter };
	}

	protected void customizeRegistration(ServletRegistration.Dynamic registration) {

		// upload temp file will put here
		File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

		// register a MultipartConfigElement
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
				maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

		registration.setMultipartConfig(multipartConfigElement);

		System.out.println("Customize Registration");
		registration.setInitParameter("dispatchOptionsRequest", "true");
		registration.setAsyncSupported(true);

	}

}
