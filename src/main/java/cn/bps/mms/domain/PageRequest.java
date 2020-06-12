package cn.bps.mms.domain;

import cn.bps.common.lang.annotation.Label;
import cn.bps.common.lang.api.Page;

import javax.validation.constraints.NotNull;

public class PageRequest {

    @NotNull
    @Label("页码")private int page;

    @NotNull
    @Label("页大小")private int size;

    public PageRequest() {
        this(0, 10);
    }

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    /**
     * get current page first item index
     * @return index
     */
    public int getIndex() {
        int offset = (this.page - 1) * this.size;
        return offset + 1;
    }

    public void initPage(Page Page){
        Page.setPage(this.page);
        Page.setSize(this.size);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
