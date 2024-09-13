import React, { useState } from "react";

function NewPost() {
    const [fileErrors, setFileErrors] = useState([]);
    const [disabled, setDisabled] = useState(false);
    return (
        <div className="card my-5">
            <form action="/newpost" method="post" encType="multipart/form-data" className="card-body">
                <div className="form-group my-2">
                    <textarea id="content" name="content" className="form-control" maxLength="8192" required></textarea>
                </div>
                <div className="form-group my-2">
                    <p id="file-errors">{fileErrors.map((error) => (
                        <span>File {error} is too large (MAX: 1MB)<br/></span>
                    ))}</p>
                    <input type="file" name="files" id="files" className="from-control-file" multiple onChange={ (e) => {
                        setFileErrors([]);
                        let shouldBeDisabled = false;
                        for(let file of e.target.files) {
                            if(file.size > 1049000) {
                                shouldBeDisabled = true;
                                setFileErrors(fileErrors.concat([file.name]));
                            }
                        }
                        setDisabled(shouldBeDisabled);


                    }}/>
                </div>
                <input value="Submit" type="submit" className="btn btn-primary my-2" disabled={disabled}/>

            </form>

        </div> 

   );
}

export default NewPost;
