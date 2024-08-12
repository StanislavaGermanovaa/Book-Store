package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.services.ProfileService;
import bg.softuni.bookstore.model.dto.UserProfileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;

@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("users/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profileData", profileService.getProfileData());

        return modelAndView;
    }

    @GetMapping("users/profile/change-info")
    public ModelAndView changeInfo() {
        ModelAndView modelAndView = new ModelAndView("change-info");
        modelAndView.addObject("profileData", profileService.getProfileData());
        return modelAndView;
    }


    @PostMapping("users/profile/update")
    public ModelAndView updateProfile(@ModelAttribute("profileData") @Valid UserProfileDTO userProfileDTO,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("change-info");
            modelAndView.addObject("profileData", userProfileDTO);
            return modelAndView;
        }

        profileService.updateProfile(userProfileDTO);
        return new ModelAndView("redirect:/users/profile");
    }
}
