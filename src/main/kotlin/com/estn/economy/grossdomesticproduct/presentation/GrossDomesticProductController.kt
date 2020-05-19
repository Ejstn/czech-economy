package com.estn.economy.grossdomesticproduct.presentation

import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/hdp")
class GrossDomesticProductController(private val fetchGdpUseCase: FetchGrossDomesticProductUseCase) {

    val template = "pages/gdp"

    @GetMapping
    fun getGdp(model: Model): String {
        model.addAttribute("nominalGdp", fetchGdpUseCase.fetchGdp(GrossDomesticProductType.NOMINAL))
        model.addAttribute("realGdp2010Prices", fetchGdpUseCase.fetchGdp(GrossDomesticProductType.REAL_2010_PRICES))

        model.addAttribute("nominalGdpChanges", fetchGdpUseCase.fetchPercentChangesPerYear(GrossDomesticProductType.NOMINAL))
        model.addAttribute("realGdpChanges", fetchGdpUseCase.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES))

        return template
    }


}