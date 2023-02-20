package com.iamstevol.Task_Tracker.controller;

import com.iamstevol.Task_Tracker.dto.LoginDto;
import com.iamstevol.Task_Tracker.dto.UserRequestDto;
import com.iamstevol.Task_Tracker.model.Task;
import com.iamstevol.Task_Tracker.model.User;
import com.iamstevol.Task_Tracker.service.Impl.TaskServiceImpl;
import com.iamstevol.Task_Tracker.service.Impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final TaskServiceImpl taskServiceImpl;


    @GetMapping("/")
    public String displayIndex(Model model) {
        model.addAttribute("user", new LoginDto());
        return "index";
    }

    @GetMapping("/signUp")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRequestDto userRequestDto) {
        User user = userService.registerUser(userRequestDto);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserRequestDto loginDto, Model model,
                            HttpSession httpSession) {
        User user = userService.loginUser(loginDto);
        httpSession.setAttribute("user", user);  //It makes user available in the homepage
        List<Task> tasks = taskServiceImpl.findAllTask(user.getUserId());
        httpSession.setAttribute("task", tasks);   //Saving the task session
        model.addAttribute("tasks", tasks);  //It will make the task available for us to use in the homepage
        if(Objects.nonNull(user)) {
            return "redirect:/home";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/regSignIn")
    public String loginUser(Model model) {
        model.addAttribute("user", new LoginDto());
        return "redirect:/";
    }

    @GetMapping("/home")
    public String getHome(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");

        List<Task> tasks = taskServiceImpl.findAllTask(user.getUserId());
        System.out.println(tasks);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);

        return "home";
    }

    @GetMapping("/pending-task")
    public String getPending(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");

        List<Task> tasks = taskServiceImpl.findByPending(user.getUserId());
        System.out.println(tasks);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);

        return "pending_task";
    }

    @GetMapping("/completed-task")
    public String getCompletedTask(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");

        List<Task> tasks = taskServiceImpl.findByCompleted(user.getUserId());
        System.out.println(tasks);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);

        return "completed_task";
    }

    @GetMapping("/progress-task")
    public String getInProgressTask(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");

        List<Task> tasks = taskServiceImpl.findByInProgress(user.getUserId());
        System.out.println(tasks);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);

        return "in_progress_task";
    }
}
