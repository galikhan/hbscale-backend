
        SELECT
            t1.*,
            t2.*,
            t3.*,
            t4.brand_desc,
            concat(
                t3.merchandising_model_cd,
                t3.model_year_nbr,
                t3.peg_option_cd,
                t3.brand_cd
            ) as super_key,
            concat(
                t3.merchandising_model_cd,
                t3.model_year_nbr,
                t3.peg_option_cd
            ) as super_model_key,
case
                when t5.model_desc is null then t1.ol_model_desc
                else t5.model_desc
            end as model_desc,
            t5.body_nm,
            t6.drp_ids
        from
            (
                select
                    order_nbr,
                    insert_ts as insert_date_ts,
                    vin,
                    latest_event_guid,
                    activity_group_nm,
                    latest_vehicle_location_key,
                    reservation_full_order_id,
                    buildable_combination_guid,
                    business_associate_key,
                    online_order_nbr,
                    sellable_flag,
                    produced_event_guid,
                    delivered_event_guid,
                    latest_ship_event_guid,
                    tag_status_nm,
                    tag_status_reason_desc,
                    visible_flag,
                    taggable_flag,
                    reservation_config_id,
                    upfitter_flag,
                    model_desc as ol_model_desc
                from
                    inventory.order_lifecycle
                where
                    insert_ts > '2022-05-18 04:00:01.000'
            ) t1
            left join (
                select
                    *
                FROM
                    inventory.buildable_vehicle_combination
            ) t2 on t1.buildable_combination_guid = t2.buildable_combination_guid
            left join (
                select
                    merchandising_model_cd,
                    model_year_nbr,
                    order_nbr,
                    brand_cd,
                    peg_option_cd
                from
                    inventory.vehicle_product
            ) t3 on t1.order_nbr = t3.order_nbr
            left join (
                select
                    *
                from
                    inventory.brand
            ) t4 on t3.brand_cd = t4.brand_cd
            left join (
                select
                    *
                from
                    inventory.merchandise_model
            ) t5 on t3.merchandising_model_cd = t5.merchandising_model_cd
            and t3.model_year_nbr = t5.model_year_nbr
            and t3.peg_option_cd = t5.peg_option_cd
            and t3.brand_cd = t5.brand_cd
            left join (
                select
                    drp_ids,
                    order_nbr
                from
                    inventory.order_detail
            ) t6 on t1.order_nbr = t6.order_nbr
