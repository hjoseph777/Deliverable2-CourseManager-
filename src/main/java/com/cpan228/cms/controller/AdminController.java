package com.cpan228.cms.controller;

import com.cpan228.cms.model.Course;
import com.cpan228.cms.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class AdminController {

    private final CourseRepository courseRepository;

    public AdminController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public String adminListCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "admin/course_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("course", course);
        return "admin/course_edit";
    }

    @PostMapping("/edit/{id}")
    public String processEdit(@PathVariable("id") Long id, @Valid @ModelAttribute("course") Course course,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            course.setId(id);
            return "admin/course_edit";
        }
        courseRepository.save(course);
        return "redirect:/admin/courses?success=true";
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        courseRepository.delete(course);
        return "redirect:/admin/courses?deleted=true";
    }
}
