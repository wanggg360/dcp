USE dcpdb;
insert into `sys_department` (dept_code, dept_name, dept_level, dept_type, dept_manager_id, dept_manager_name, parent_dept_code, status, create_time, update_time, create_by, update_by, remark) values ('ZZ000001', '华泰证券', '1', '1', '000001', '管理员1', '', '0', '2022-10-25 00:00:00', '2022-10-25 00:00:00', '000001', '000001', '集团总部层级');

insert into `sys_department` (dept_code, dept_name, dept_level, dept_type, dept_manager_id, dept_manager_name, parent_dept_code, status, create_time, update_time, create_by, update_by, remark) values ('ZZ000002', '华泰金控', '2', '2', '000002', '管理员2', 'ZZ000001', '0', '2022-10-25 00:00:00', '2022-10-25 00:00:00', '000001', '000001', '金控子公司');

insert into `sys_department` (dept_code, dept_name, dept_level, dept_type, dept_manager_id, dept_manager_name, parent_dept_code, status, create_time, update_time, create_by, update_by, remark) values ('ZZ000003', '华泰期货', '2', '2', '000003', '管理员3', 'ZZ000001', '0', '2022-10-25 00:00:00', '2022-10-25 00:00:00', '000001', '000001', '期货子公司');

insert into `sys_department` (dept_code, dept_name, dept_level, dept_type, dept_manager_id, dept_manager_name, parent_dept_code, status, create_time, update_time, create_by, update_by, remark) values ('ZZ200001', '南京分公司', '2', '3', '000005', '管理员5', 'ZZ000001', '0', '2022-10-25 00:00:00', '2022-10-25 00:00:00', '000001', '000001', '集团分公司');

insert into `sys_department` (dept_code, dept_name, dept_level, dept_type, dept_manager_id, dept_manager_name, parent_dept_code, status, create_time, update_time, create_by, update_by, remark) values ('ZZ300001', 'XXXX部', '2', '1', '000006', '管理员6', 'ZZ000001', '0', '2022-10-25 00:00:00', '2022-10-25 00:00:00', '000001', '000001', '集团部门');

insert into `sys_department` (dept_code, dept_name, dept_level, dept_type, dept_manager_id, dept_manager_name, parent_dept_code, status, create_time, update_time, create_by, update_by, remark) values ('ZZ200021', 'XXXX营业部', '3', '1', '000009', '管理员9', 'ZZ200001', '0', '2022-10-25 00:00:00', '2022-10-25 00:00:00', '000001', '000001', '分支营业部');
