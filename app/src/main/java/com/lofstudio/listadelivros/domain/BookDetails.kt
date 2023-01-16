package com.lofstudio.listadelivros.domain

class BookDetails {



    var title: String? = ""
    var description: String? = ""
    var contributor: String? = ""
    var author: String? = ""
    var contributor_note: String? = ""
    var price: Float? = 0.0f
    var age_group: String? = ""
    var publisher: String? = ""
    var primary_isbn13: String? = ""
    var primary_isbn10: String? = ""




    constructor(
        title: String?,
        description: String?,
        contributor: String?,
        author: String?,
        contributor_note: String?,
        price: Float?,
        age_group: String?,
        publisher: String?,
        primary_isbn13: String?,
        primary_isbn10: String?
    ) {
        this.title = title
        this.description = description
        this.contributor = contributor
        this.author = author
        this.contributor_note = contributor_note
        this.price = price
        this.age_group = age_group
        this.publisher = publisher
        this.primary_isbn13 = primary_isbn13
        this.primary_isbn10 = primary_isbn10
    }

    constructor()
}