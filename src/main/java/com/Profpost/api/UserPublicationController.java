package com.Profpost.api;
import com.Profpost.model.entity.Category;
import com.Profpost.dto.CategoryDTO;
import com.Profpost.dto.PublicationDTO;
import com.Profpost.model.entity.Publication;
import com.Profpost.model.entity.User;
import com.Profpost.model.enums.Role;
import com.Profpost.service.CategoryService;
import com.Profpost.service.UserPublicationService;
import com.Profpost.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Profpost.mapper.CategoryMapper;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/publications")
public class UserPublicationController {
    private final UserPublicationService userPublicationService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<Publication>> list(){
        List<Publication> publications = userPublicationService.findAll();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> get(@PathVariable Integer id){
        Publication publication = userPublicationService.findById(id);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @PostMapping("/creators")
    public ResponseEntity<Publication> create(@RequestBody PublicationDTO publicationDTO){
        User user = userService.findById(publicationDTO.getUser_id());
        CategoryDTO categoryDTO = categoryService.findById(publicationDTO.getCategory_id());

        if(user.getRole() != Role.CREATOR){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Category category = categoryMapper.toEntity(categoryDTO);
        Publication publication = userPublicationService.create(publicationDTO.toPublication(category, user));
        return new ResponseEntity<>(publication, HttpStatus.CREATED);
    }

    @PutMapping("/creators/{id}")
    public ResponseEntity<Publication> update(@PathVariable Integer id, @RequestBody PublicationDTO publicationDTO){
        Publication existingPublication = userPublicationService.findById(id);

        if (publicationDTO.getCategory_id() != null) {
            CategoryDTO categoryDTO = categoryService.findById(publicationDTO.getCategory_id());

            // Convertir CategoryDTO a Category
            Category category = categoryMapper.toEntity(categoryDTO);

            existingPublication.setCategory(category);
        }

        existingPublication.setTitle(publicationDTO.getTitle());
        existingPublication.setContent(publicationDTO.getContent());
        existingPublication.setVideo_url(publicationDTO.getVideo_url());
        existingPublication.setSchedulePublishAt(publicationDTO.getSchedulePublishAt());
        existingPublication.setUpdatedAt(LocalDateTime.now());

        Publication updatedPublication = userPublicationService.update(id, existingPublication);
        return new ResponseEntity<>(updatedPublication, HttpStatus.OK);
    }

    @DeleteMapping("/creators/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        userPublicationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}