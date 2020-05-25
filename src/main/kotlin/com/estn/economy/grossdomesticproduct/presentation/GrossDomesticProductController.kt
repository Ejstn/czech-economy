package com.estn.economy.grossdomesticproduct.presentation

import com.estn.economy.core.presentation.Breadcrumbs
import com.estn.economy.core.presentation.Gdp
import com.estn.economy.core.presentation.Home
import com.estn.economy.core.presentation.mapToPairs
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
        model.addAttribute("nominalGdp", fetchGdpUseCase.fetchGdp(GrossDomesticProductType.NOMINAL)
                .mapToPairs())

        model.addAttribute("nominalGdpChanges", fetchGdpUseCase.fetchPercentChangesPerYear(GrossDomesticProductType.NOMINAL)
                .mapToPairs())

        model.addAttribute("realGdp2010Prices", fetchGdpUseCase.fetchGdp(GrossDomesticProductType.REAL_2010_PRICES)
                .mapToPairs())

        model.addAttribute("realGdpChanges", fetchGdpUseCase.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES)
                .mapToPairs())

        model.addAttribute("breadcrumbs", Breadcrumbs(listOf(Home, Gdp)))

        return template
    }


}