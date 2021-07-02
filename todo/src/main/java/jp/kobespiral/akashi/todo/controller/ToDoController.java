package jp.kobespiral.akashi.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobespiral.akashi.todo.dto.LoginForm;
import jp.kobespiral.akashi.todo.dto.ToDoForm;
import jp.kobespiral.akashi.todo.entity.Member;
import jp.kobespiral.akashi.todo.entity.ToDo;
import jp.kobespiral.akashi.todo.service.MemberService;
import jp.kobespiral.akashi.todo.service.ToDoService;

@Controller
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    MemberService mService;
    @Autowired
    ToDoService tService;

    /**
     * ログイン画面の表示
     * @return
     */
    @GetMapping("/")
    public String showIndex(@ModelAttribute(name = "loginForm") LoginForm form, Model model) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaa");
        model.addAttribute("loginForm", new LoginForm());
        return "index";
    }

    /**
     * 特定のmidを持つ人のToDo等をmodelにセットする
     * @param mid
     * @param model
     * @return
     */
    @GetMapping("/{mid}")
    public String showList(@PathVariable String mid, Model model) {
        Member m = mService.getMember(mid);
        model.addAttribute("name", m.getName());
        model.addAttribute("mid", m.getMid());
        List<ToDo> myToDos = tService.getToDoList(mid);
        List<ToDo> myDones = tService.getDoneList(mid);
        model.addAttribute("myToDos", myToDos);
        model.addAttribute("myDones", myDones);

        model.addAttribute("toDoForm", new ToDoForm());

        return "list";
    }

    /**
     * ログイン時に呼ばれるメソッド
     * midとそれに対応する名前をmodelに入れてlist.htmlを返す
     * listで表示させる自分のtodoとdoneをmodelに格納する
     * @param mid
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute(name = "loginForm") LoginForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showIndex(form, model);
        }
        model.addAttribute("loginForm", form);
        String mid = form.getMid();
        //mService.getMember(mid);
        //model.addAttribute("name", m.getName());
        //model.addAttribute("mid", m.getMid());

        return "redirect:/todo/" + mid;
    }
    
    /**
     * todoの登録を行う
     * @param form
     * @param mid
     * @param model
     * @return
     */
    @PostMapping("/{mid}/register")
    public String createToDo(@ModelAttribute("toDoForm") ToDoForm form, @PathVariable String mid, Model model) {
        model.addAttribute("toDoForm", form);
        tService.createToDo(mid, form);
        return "redirect:/todo/" + mid;
    }

    /**
     * todoをdoneにする
     * @param seq
     * @param mid
     * @return
     */
    @PostMapping("/{mid}/done")
    public String done(@RequestParam Long seq, @PathVariable String mid){
        tService.done(seq);

        return "redirect:/todo/" + mid;
    }

    /**
     * 全員のToDo，Doneリストを表示する
     * @param model
     * @return
     */
    @PostMapping("/{mid}/all")
    public String showAllList(@PathVariable String mid, Model model) {
        model.addAttribute("mid", mid);
        List<ToDo> allToDos = tService.getAllToDoList();
        List<ToDo> allDones = tService.getAllDoneList();
        model.addAttribute("allToDos", allToDos);
        model.addAttribute("allDones", allDones);

        return "alllist";
    }
}
