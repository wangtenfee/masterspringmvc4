package com.example.profile;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.config.PictureUploadProperties;

import java.io.*;
import java.net.URLConnection;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {
    private final Resource picturesDir;
    private final Resource anonymousPicture;
    private final MessageSource messageSource;
    
    
    
    @Autowired
    public PictureUploadController(PictureUploadProperties uploadProperties,MessageSource messageSource) {
        picturesDir = uploadProperties.getUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
        this.messageSource = messageSource;
    }

    @ModelAttribute("picturePath")
    public Resource picturePath() {
        return anonymousPicture;
    }

    @RequestMapping("upload")
    public String uploadPage() {
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response,@ModelAttribute("picturePath") Resource picturePath) throws IOException {
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.getFilename()));
        IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Model model) throws IOException {

        if (file.isEmpty() || !isImage(file)) {
            redirectAttrs.addFlashAttribute("error", "Incorrect file. Please upload a picture.");
            return "redirect:/upload";
        }

        Resource picturePath = copyFileToPictures(file);
        model.addAttribute("picturePath", picturePath);

        return "profile/uploadPage";
    }
    
    //handling upload error
//	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Model model) throws IOException {
//		throw new IOException("Some message");
//	}

    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)) {

            IOUtils.copy(in, out);
        }
        return new FileSystemResource(tempFile);
    }

    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }
    
	@ExceptionHandler(IOException.class)
	//public ModelAndView handleIOException(IOException exception) {
	public ModelAndView handleIOException(Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
		//modelAndView.addObject("error", exception.getMessage());
		modelAndView.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
		return modelAndView;
	}
	
	@RequestMapping("uploadError")
//	public ModelAndView onUploadError(HttpServletRequest request) {
	public ModelAndView onUploadError(Locale locale) {
		ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
		//modelAndView.addObject("error", request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE));
		modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
		return modelAndView;
	}
}
