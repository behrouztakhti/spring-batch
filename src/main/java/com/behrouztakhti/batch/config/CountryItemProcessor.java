package com.behrouztakhti.batch.config;

import com.behrouztakhti.batch.domain.Country;
import com.behrouztakhti.batch.dto.CountryDTO;
import org.springframework.batch.item.ItemProcessor;

/**
 * processor class.
 * @author behrouz.takhti@gmail.com
 */
public class CountryItemProcessor implements ItemProcessor<CountryDTO, Country> {


    /**
     * This method processes input data after reading.
     * @param countryDTO an object of CountryDTO which has produced by reader.
     * @return an object of Country class.
     */
    @Override
    public Country process(CountryDTO countryDTO) throws Exception {
        return new Country(countryDTO.getUnloCode().toUpperCase(), countryDTO.getName());
    }





}
