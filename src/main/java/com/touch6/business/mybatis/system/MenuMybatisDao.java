package com.touch6.business.mybatis.system;

import com.touch6.business.entity.system.Menu;
import com.touch6.business.entity.system.Module;
import com.touch6.business.mybatis.common.MyBatisRepository;

import java.util.List;

/*
 * ============================================================================		
 * = COPYRIGHT		
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION		
 *   This software is supplied under the terms of a license agreement or		
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied		
 *   or disclosed except in accordance with the terms in that agreement.		
 *      Copyright (C) 2017-? PAX Technology, Inc. All rights reserved.		
 * Description: // Detail description about the function of this module,		
 *             // interfaces with the other modules, and dependencies. 		
 * Revision History:		
 * Date	                 Author	                  Action
 * 2017/2/23  	         zhuxl@paxsz.com        Create/Add/Modify/Delete
 * ============================================================================		
 */
@MyBatisRepository
public interface MenuMybatisDao {
    int insertMenu(Menu menu);

    Menu findByMenuId(Long menuId);

    int updateMenu(Menu menu);

    List<Menu> findAll();

    List<Menu> findByModuleId(Long moduleId);

    int deleteMenu(Long menuId);
}
