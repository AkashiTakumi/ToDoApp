package jp.kobespiral.akashi.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showIndex() {
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
    public String login(@RequestParam String mid, Model model) {
        Member m = mService.getMember(mid);
        model.addAttribute("name", m.getName());
        model.addAttribute("mid", m.getMid());
        //List<ToDo> myToDos = tService.getToDoList(mid);
        //List<ToDo> myDones = tService.getDoneList(mid);
        //model.addAttribute("myToDos", myToDos);
        //model.addAttribute("myDones", myDones);

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
    public String createToDo(@ModelAttribute("ToDoForm") ToDoForm form, @PathVariable String mid, Model model) {
        model.addAttribute("ToDoForm", form);
        tService.createToDo(mid, form);
        return "redirect:/todo/" + mid;
    }

    @PostMapping("/{mid}/all")
    public String showAllList(Model model) {
        List<ToDo> allToDos = tService.getAllToDoList();
        List<ToDo> allDones = tService.getAllDoneList();
        model.addAttribute("allToDos", allToDos);
        model.addAttribute("allDones", allDones);

        return "alllist";
    }
}
