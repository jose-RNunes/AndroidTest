package br.com.androidtest.domain.models.mock

import br.com.androidtest.domain.models.myplan.MyPlanItemHeader

object ItemsHeaderMock {
    val itemsHeader: List<MyPlanItemHeader> = arrayListOf(
        MyPlanItemHeader(
            title = "Status:",
            textValue = "Active",
            alignment = "left",
            textColor = "#1F1D1D"
        ),
        MyPlanItemHeader(
            title = "Meu número:",
            textValue = "(34) 99687-7876",
            alignment = "left",
            textColor = "#1F1D1D"
        ),
        MyPlanItemHeader(
            title = "Plano",
            textValue = "15GB",
            alignment = "right",
            textColor = "#1F1D1D"
        ),
        MyPlanItemHeader(
            title = "Bônus para redes sociais",
            textValue = "5GB",
            alignment = "right",
            textColor = "#1F1D1D"
        )
    )
}