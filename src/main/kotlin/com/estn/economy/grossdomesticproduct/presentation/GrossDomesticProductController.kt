package com.estn.economy.grossdomesticproduct.presentation

import com.estn.economy.core.presentation.model.Gdp
import com.estn.economy.core.presentation.model.Home
import com.estn.economy.core.presentation.model.Routing
import com.estn.economy.core.presentation.utility.addBreadcrumbs
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(Routing.GDP)
class GrossDomesticProductController(private val fetchGdpUseCase: FetchGrossDomesticProductUseCase) {

    val template = "pages/gdp"

    @GetMapping
    fun getGdp(model: Model): String {
        model.addAttribute("realGdp2010Prices", fetchGdpUseCase.fetchGdp(GrossDomesticProductType.REAL_2010_PRICES)
                .mapToPairs())

        model.addAttribute("realGdpChanges", fetchGdpUseCase.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES)
                .mapToPairs())

        model.addBreadcrumbs(Home, Gdp)

        return template
    }


}