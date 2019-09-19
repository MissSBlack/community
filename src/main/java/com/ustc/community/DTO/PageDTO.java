package com.ustc.community.DTO;

import java.util.ArrayList;
import java.util.List;

public class PageDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page; //分页时所显示的当前页
    private List<Integer> pages=new ArrayList<>();//分页时，在分页栏显示的那些页码
    private Integer totalPage;

    public void setPageInfor(Integer totalCount, Integer page, Integer size) {
        this.page=page;
        pages.add(page);
        //将对应的要显示的页码放在pages中
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=this.totalPage){
                pages.add(page+i);
            }
        }
        //是否展示上一页按钮
        if(page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页按钮
        if(page==this.totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否展示跳转第一页按钮
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //是否展示跳转最后一页按钮
        if(pages.contains(this.totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }

    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
