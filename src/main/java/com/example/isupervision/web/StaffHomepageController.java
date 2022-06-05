package com.example.isupervision.web;

import com.example.isupervision.entities.works.Work;
import com.example.isupervision.entities.works.WorkLimits;
import com.example.isupervision.services.WorkService;
import com.example.isupervision.web.dto.WorkDTO;
import com.example.isupervision.web.dto.WorkLimitsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

import static com.example.isupervision.entities.works.WorkType.*;

@Controller
public class StaffHomepageController {
    private WorkLimits limits;
    public final WorkService workService;

    public StaffHomepageController(
            WorkService workService
    ){
        this.workService = workService;
    }

    @ModelAttribute("workLimits")
    public WorkLimitsDTO workLimitsDTO(){return new WorkLimitsDTO();}

    @ModelAttribute("work")
    public WorkDTO workDTO(){return new WorkDTO();}



    @GetMapping("/staffHomepage")
    public String showStaffHomepage(Model model){
        List<Work> projects = workService.fetchWorksByType(PROJECT);
        List<Work> bachelors = workService.fetchWorksByType(BACHELOR);
        List<Work> masters = workService.fetchWorksByType(MASTER);

        limits = workService.fetchLimits();

        model.addAttribute("projectLimit", limits.getMaxNumOfProjects());
        model.addAttribute("bachelorLimit", limits.getMaxNumOfBachelor());
        model.addAttribute("masterLimit", limits.getMaxNumOfMaster());
        model.addAttribute("projects", projects);
        model.addAttribute("bachelors", bachelors);
        model.addAttribute("masters", masters);
        return "staffHomepage";
    }

    @GetMapping("/staffHomepage/newWork")
    public String newWorkPage(Model model){
        WorkDTO work = new WorkDTO();
        model.addAttribute("work", work);
        model.addAttribute("date", new Date());
        return "newWork";
    }

    @PostMapping("/staffHomepage/newWork/addWork")
    public String saveWork(
            @ModelAttribute("work") WorkDTO workDTO,
            RedirectAttributes redirectAttributes
    ){
        limits = workService.fetchLimits();
        if (!allFieldsAreFilled(workDTO)){
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Please fill all the fields!");
            return "redirect:/staffHomepage/newWork";
        }
        if (workDTO.getTermDate().before(workDTO.getDeadline())){
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Term Date should not be before deadline!");
            return "redirect:/staffHomepage/newWork";
        }
        switch (workDTO.getType()){
            case PROJECT -> {
                if (limits.getMaxNumOfProjects() <= workService.fetchWorksByType(PROJECT).size()) {
                    redirectAttributes.addFlashAttribute(
                            "error",
                            "You have reached the maximum number of projects! " +
                                    "To add a new one the limit should be increased first!");
                    return "redirect:/staffHomepage/newWork";
                }
            }case BACHELOR -> {
                if (limits.getMaxNumOfBachelor() <= workService.fetchWorksByType(BACHELOR).size()) {
                    redirectAttributes.addFlashAttribute(
                            "error",
                            "You have reached the maximum number of bachelors! " +
                                    "To add a new one the limit should be increased first!");
                    return "redirect:/staffHomepage/newWork";
                }
            }case MASTER -> {
                if (limits.getMaxNumOfMaster() <= workService.fetchWorksByType(MASTER).size()) {
                    redirectAttributes.addFlashAttribute(
                            "error",
                            "You have reached the maximum number of masters! " +
                                    "To add a new one the limit should be increased first!");
                    return "redirect:/staffHomepage/newWork";
                }
            }
        }
        workService.saveWork(workDTO);
        redirectAttributes.addFlashAttribute("success", "You have successfully created a new Work!");
        return "redirect:/staffHomepage";
    }

    private boolean allFieldsAreFilled(WorkDTO workDTO) {
        if (workDTO.getTitle().isEmpty()
                || workDTO.getDescription().isEmpty()
                || workDTO.getTermDate() == null
                || workDTO.getDeadline() == null){
            return false;
        }return true;
    }

    @PostMapping("/staffHomepage/setLimits")
    public String setLimitsProj(
            @ModelAttribute("workLimits") WorkLimitsDTO limitsDTO,
            RedirectAttributes redirectAttributes
    ){
        switch (limitsDTO.getWorkType()) {
            case PROJECT -> {
                if (limitsDTO.getLimit() == limits.getMaxNumOfProjects() ||
                        limitsDTO.getLimit() < workService.fetchWorksByType(PROJECT).size()) {
                    redirectAttributes.addFlashAttribute(
                            "error",
                            "The new limit should not be smaller than the actual number of projects!");
                    return "redirect:/staffHomepage";
                }
                else limits.setMaxNumOfProjects(limitsDTO.getLimit());
            }
            case BACHELOR -> {
                if (limitsDTO.getLimit() == limits.getMaxNumOfBachelor() ||
                        limitsDTO.getLimit() < workService.fetchWorksByType(BACHELOR).size()) {
                    redirectAttributes.addFlashAttribute(
                            "error",
                            "The new limit should not be smaller than the actual number of bachelors!");
                    return "redirect:/staffHomepage";
                }
                else limits.setMaxNumOfBachelor(limitsDTO.getLimit());
            }
            case MASTER -> {
                if (limitsDTO.getLimit() == limits.getMaxNumOfMaster() ||
                        limitsDTO.getLimit() < workService.fetchWorksByType(MASTER).size()) {
                    redirectAttributes.addFlashAttribute(
                            "error",
                            "The new limit should not be smaller than the actual number of masters!");
                    return "redirect:/staffHomepage";
                }
                else limits.setMaxNumOfMaster(limitsDTO.getLimit());
            }
        }
        workService.updateLimits(limits);
        redirectAttributes.addFlashAttribute(
                "success",
                "You have successfully set a new limit");
        return "redirect:/staffHomepage";
    }

    @GetMapping("/staffHomepage/edit/{id}")
    public String editWork(@PathVariable Long id, Model model){
        model.addAttribute("work", workService.getWorkById(id));
        return "editWork";
    }

    @PostMapping("/staffHomepage/editWork/{id}")
    public String updateWork(
            @PathVariable Long id,
            @ModelAttribute("work") WorkDTO newWork,
            RedirectAttributes redirectAttributes
    ){

        if (!allFieldsAreFilled(newWork)){
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Please fill all the fields!");
            return "redirect:/staffHomepage/newWork";
        }
        workService.updateWork(newWork, id);
        redirectAttributes.addFlashAttribute("success", "Successfully updated!");
        return "redirect:/staffHomepage";
    }

    @RequestMapping(value = "/staffHomepage/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteWork(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ){
        workService.deleteWorkById(id);
        redirectAttributes.addFlashAttribute("success", "Successfully deleted!");
        return "redirect:/staffHomepage";
    }

    @GetMapping("/staffHomepage/approve/{id}")
    public String approveWork(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ){
        workService.approveWorkById(id);
        redirectAttributes.addFlashAttribute("success", "Approved!");
        return "redirect:/staffHomepage";
    }
}






















