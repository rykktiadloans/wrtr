import React from "react";
import { Helmet } from "react-helmet-async";

/**
 * @param {{title: string, description: string}} props Title and description of the page
 * @returns {JSX.Element} MetaTags component
 */
function MetaTags({title="", description=""}) {
    return (
        <Helmet>
            <title>{title}</title>
            <meta property="description" content={description}/>
        </Helmet>
    )

}

export default MetaTags;
