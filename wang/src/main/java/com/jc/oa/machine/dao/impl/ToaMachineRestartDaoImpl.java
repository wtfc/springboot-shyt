package com.jc.oa.machine.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.util.ReflectHelper;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.dao.IToaMachineRestartDao;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 重启操作
 */
@Repository
public class ToaMachineRestartDaoImpl extends BaseDaoImpl<ToaMachineRestart> implements IToaMachineRestartDao{
	
	public ToaMachineRestartDaoImpl(){}

	@Override
	public PageManager queryAll(ToaMachineRestart toaMachineRestart,
			PageManager page) throws CustomException {
		ReflectHelper.escapeSQLSpecialChar(toaMachineRestart);
		PageManager page_ = new PageManager();
		// 查询行数 mapping xml中返回类型有的是long有的是integer，临时处理办法
		Object rowsCountObject = template.selectOne(getNameSpace(toaMachineRestart) + "."+"queryCountAll",toaMachineRestart);
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

		List<ToaMachineRestart> list = template.selectList(
				getNameSpace(toaMachineRestart) + "."+"queryAll",toaMachineRestart,
				new RowBounds((page.getPage()) * page.getPageRows(), page
						.getPageRows()));

		page_.setTotalPages(pageCount);
		page_.setData(list);
		page_.setsEcho(page.getsEcho());
		return page_;
	}

	@Override
	public List<ToaMachineRestart> queryApp(ToaMachineRestart toaMachineRestart) throws CustomException {
		return template.selectList(getNameSpace(toaMachineRestart)+"."+"queryApp", toaMachineRestart);
	}

}