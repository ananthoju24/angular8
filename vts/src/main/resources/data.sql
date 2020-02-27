insert into T_USER_DETAILS(F_ID,F_USER_ID,F_PASSWORD, F_CREATE_DATE,F_UPDATE_DATE) values(1,'admin','1234',sysdate,sysdate);
insert into T_USER_DETAILS(F_ID,F_USER_ID,F_PASSWORD, F_CREATE_DATE,F_UPDATE_DATE) values(2,'arun','1234',sysdate,sysdate);
 
insert into t_owner_details (f_create_date, f_update_date, f_father_or_husband_name, f_mobile_number, f_owner_name, f_owner_id) values (now(), now(), 'father', '8019692762', 'Arun', 11644);

insert into t_house_details (f_create_date, f_update_date, f_owner_id, f_house_no) values (now(), now(),11644, '1-23'); 

insert into t_tax_details (f_create_date, f_update_date, f_bandhela_doddi, f_building_tax, f_cleaning_tax, f_dakhala_fee, f_drainage_tax, f_house_con_fee, f_house_tax, f_kulai_deposit, f_kulai_nela_vari_fee, f_library_tax, f_license_tax, f_lighthing_tax, f_other_value, f_other_key, f_tax_status, f_tax_year, f_total_tax, f_water_tax, f_house_id, f_tax_id) values (now(), now(), 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 'other key', 'PENDING','2019',130 , '10', '1-23', 1000000);

insert into t_tax_details (f_create_date, f_update_date, f_bandhela_doddi, f_building_tax, f_cleaning_tax, f_dakhala_fee, f_drainage_tax, f_house_con_fee, f_house_tax, f_kulai_deposit, f_kulai_nela_vari_fee, f_library_tax, f_license_tax, f_lighthing_tax, f_other_value, f_other_key, f_tax_status, f_tax_year, f_total_tax, f_water_tax, f_house_id, f_tax_id) values (now(), now(), 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 'other key', 'PENDING','2020',130 , '10', '1-23', 1000001);

insert into t_transaction_details (f_create_date, f_update_date, f_bandhela_doddi, f_building_tax, f_cleaning_tax, f_dakhala_fee, f_drainage_tax, f_house_con_fee, f_house_tax, f_kulai_deposit, f_kulai_nela_vari_fee, f_library_tax, f_license_tax, f_lighthing_tax, f_other_value, f_other_key, f_tax_id, f_tax_status, f_tax_year, f_total_tax, f_water_tax, f_tran_id) values (now(), now(), 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10, 10, 10,'other tax', 1000001, 'SUCCESS', 2019, 130,10,100000000);
