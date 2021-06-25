package jp.kobespiral.akashi.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * ログイン時に呼ばれるメソッド
     * midとそれに対応する名前をmodelに入れてlist.htmlを返す
     * listで表示させる自分のtodoとdoneをmodelに格納する
     * @param mid
     * @param model
     * @return
     */
    @PostMapping("/list")
    public String login(@RequestParam String mid, Model model) {
        Member m = mService.getMember(mid);
        model.addAttribute("name", m.getName());
        model.addAttribute("mid", m.getMid());
        List<ToDo> myToDos = tService.getToDoList(mid);
        List<ToDo> myDones = tService.getDoneList(mid);
        model.addAttribute("myToDos", myToDos);
        model.addAttribute("myDones", myDones);

        return "list";
    }

    @PostMapping("/list/all")
    public String () {
        
    }
}
