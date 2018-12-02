package com.jc.oa.finance.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.util.ReflectHelper;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.dao.IToaFinanceInvoicesDao;
/**
 * @author mrb
 * @version 月收入
 */
@Repository
public class ToaFinanceInvoicesDaoImpl extends BaseDaoImpl<ToaFinanceInvoices> implements IToaFinanceInvoicesDao{
	
	public ToaFinanceInvoicesDaoImpl(){}

	@Override
	public List<ToaFinanceInvoices> getMain(ToaFinanceInvoices toaFinanceInvoices) {
		return template.selectList(getNameSpace(toaFinanceInvoices)+"."+"queryBill", toaFinanceInvoices);
	}

	@Override
	public Integer billState(ToaFinanceInvoices toaFinanceInvoices) {
		// TODO Auto-generated method stub
		return template.update(getNameSpace(toaFinanceInvoices)+"."+"billState",toaFinanceInvoices);
	}

	@Override
	public PageManager mainInvoices(ToaFinanceInvoices toaFinanceInvoices,PageManager page) {
		ReflectHelper.escapeSQLSpecialChar(toaFinanceInvoices);
		PageManager page_ = new PageManager();
		// 查询行数 mapping xml中返回类型有的是long有的是integer，临时处理办法
		Object rowsCountObject = template.selectOne(getNameSpace(toaFinanceInvoices) + "."+"queryBillCount",toaFinanceInvoices);
		int rowsCount = 0;
		if(rowsCountObject instanceof Long){
			rowsCount = ((Long) rowsCountObject).intValue();
		}
		else {
			rowsCount = ((Integer) rowsCountObject).intValue();
		}
		page_.setTotalCount(rowsCount);
		if(page.getPageRows()==-1){
			page.setPageRows(Integer.MAX_VALUE);
		}
		// 计算页数 page.getRows()获得每页显示条数，系统中固定值
		int pageCount = rowsCount / page.getPageRows();
		if (rowsCount % page.getPageRows() > 0) {
			pageCount++;
		}
		// 如果传过来的当前页码大于总页码 则把当前页码设置为最大页码
		if (page.getPage() + 1 > pageCount && pageCount != 0) {
			page.setPage(pageCount);
		}

		// 将页面传过来的当前页传回到前台
		page_.setPage(page.getPage() + 1);

		List<ToaFinanceInvoices> list = template.selectList(
				getNameSpace(toaFinanceInvoices) + "."+"queryBill",toaFinanceInvoices,
				new RowBounds((page.getPage()) * page.getPageRows(), page
						.getPageRows()));

		page_.setTotalPages(pageCount);
		page_.setData(list);
		page_.setsEcho(page.getsEcho());
		return page_;
	}
	
	@Override
	public List<ToaFinanceInvoices> queryInvoices(ToaFinanceInvoices toaFinanceInvoices) {
		// TODO Auto-generated method stub
		return template.selectList(getNameSpace(toaFinanceInvoices)+"."+"queryInvoices", toaFinanceInvoices);
	}

	@Override
	public List<ToaFinanceInvoices> queryInvoicesName(ToaFinanceInvoices toaFinanceInvoices) {
		// TODO Auto-generated method stub
		return template.selectList(getNameSpace(toaFinanceInvoices)+"."+"queryInvoicesName", toaFinanceInvoices);
	}

	@Override
	public Integer billNewState(ToaFinanceInvoices toaFinanceInvoices) {
		// TODO Auto-generated method stub
		return template.update(getNameSpace(toaFinanceInvoices)+"."+"billNewState",toaFinanceInvoices);
	}
}
