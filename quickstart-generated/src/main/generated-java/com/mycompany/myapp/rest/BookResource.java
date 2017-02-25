/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/rest/EntityResource.java.e.vm
 */
package com.mycompany.myapp.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.dto.BookDTO;
import com.mycompany.myapp.dto.BookDTOService;
import com.mycompany.myapp.dto.support.PageRequestByExample;
import com.mycompany.myapp.dto.support.PageResponse;
import com.mycompany.myapp.repository.BookRepository;
import com.mycompany.myapp.rest.support.AutoCompleteQuery;

@RestController
@RequestMapping("/api/books")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    @Inject
    private BookRepository bookRepository;
    @Inject
    private BookDTOService bookDTOService;

    /**
     * Create a new Book.
     */
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) throws URISyntaxException {

        log.debug("Create BookDTO : {}", bookDTO);

        if (bookDTO.isIdSet()) {
            return ResponseEntity.badRequest().header("Failure", "Cannot create Book with existing ID").body(null);
        }

        BookDTO result = bookDTOService.save(bookDTO);

        return ResponseEntity.created(new URI("/api/books/" + result.id)).body(result);
    }

    /**
    * Find by id Book.
    */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> findById(@PathVariable Integer id) throws URISyntaxException {

        log.debug("Find by id Book : {}", id);

        return Optional.ofNullable(bookDTOService.findOne(id)).map(bookDTO -> new ResponseEntity<>(bookDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update Book.
     */
    @RequestMapping(value = "/", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO bookDTO) throws URISyntaxException {

        log.debug("Update BookDTO : {}", bookDTO);

        if (!bookDTO.isIdSet()) {
            return create(bookDTO);
        }

        BookDTO result = bookDTOService.save(bookDTO);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Target url for extractBinary file upload.
     */
    @RequestMapping(value = "/{id}/upload/extractBinary", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> extractBinaryFileUpload(@PathVariable Integer id, @RequestParam("extractBinary") MultipartFile multipartFile) {

        log.debug("File Upload: {}", multipartFile.getName());

        Book book = bookRepository.findOne(id);

        try {
            book.setExtractBinary(multipartFile.getBytes());
        } catch (IOException ioe) {
            // todo: appropriate status code
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        book.setExtractSize(multipartFile.getSize());
        book.setExtractContentType(multipartFile.getContentType());
        book.setExtractFileName(multipartFile.getOriginalFilename());
        bookRepository.save(book);

        return ResponseEntity.ok().build();
    }

    /**
     * File download facility for extractBinary.
     */
    @RequestMapping(value = "/{id}/download/extractBinary", method = GET)
    @ResponseBody
    public ResponseEntity<byte[]> extractBinaryFileDownload(@PathVariable Integer id) {

        Book book = bookRepository.findOne(id);

        log.debug("File Download: {}", book.getExtractFileName());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, book.getExtractContentType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getExtractFileName() + "\"").contentLength(book.getExtractSize())
                .body(book.getExtractBinary());
    }

    /**
     * Find a Page of Book using query by example.
     */
    @RequestMapping(value = "/page", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<BookDTO>> findAll(@RequestBody PageRequestByExample<BookDTO> prbe) throws URISyntaxException {
        PageResponse<BookDTO> pageResponse = bookDTOService.findAll(prbe);
        return new ResponseEntity<>(pageResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
    * Auto complete support.
    */
    @RequestMapping(value = "/complete", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> complete(@RequestBody AutoCompleteQuery acq) throws URISyntaxException {

        List<BookDTO> results = bookDTOService.complete(acq.query, acq.maxResults);

        return new ResponseEntity<>(results, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Delete by id Book.
     */
    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws URISyntaxException {

        log.debug("Delete by id Book : {}", id);

        try {
            bookRepository.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception x) {
            // todo: dig exception, most likely org.hibernate.exception.ConstraintViolationException
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}