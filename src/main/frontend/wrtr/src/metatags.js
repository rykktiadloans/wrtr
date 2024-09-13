import React from "react";
import { Helmet } from "react-helmet-async";

function MetaTags({title="", description=""}) {
    return (
        <Helmet>
            <title>{title}</title>
            <meta property="description" content={description}/>
        </Helmet>
    )

}

export default MetaTags;
