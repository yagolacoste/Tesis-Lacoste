package com.Tesis.admin.Controller.StoredDocument;


import com.Tesis.admin.Models.StoredDocument;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(IStoredDocumentController.PATH)
public interface IStoredDocumentController {
    public static final String PATH="/storeddocument";


    @GetMapping("/")
    public List<StoredDocument> list();


    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam(value = "fileName") String fileName, HttpServletRequest request);

    @PostMapping("/")
    public Long save(@ModelAttribute StoredDocument obj) ;


//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<Resource> downloadByFileName(@PathVariable String fileName, HttpServletRequest request);


}
