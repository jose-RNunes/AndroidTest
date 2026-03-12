package br.com.androidtest.domain.models.mock

import br.com.androidtest.domain.models.myplan.MyPlanDomain
import br.com.androidtest.domain.models.myplan.MyPlanExtraPlay

object MyPlanDomainMock {
    fun getMyPlanMock(): MyPlanDomain {
        return MyPlanDomain(
            screnName = "Plano Claro Flex",
            offerDisplay = "20GB",
            extraPlay = MyPlanExtraPlay("Apps Inclusos", arrayListOf()),
            status ="",
            planValue = "",
            headers = arrayListOf(),
            extrasIcons = arrayListOf(),
            phoneNumber = ""
        )
    }
}
