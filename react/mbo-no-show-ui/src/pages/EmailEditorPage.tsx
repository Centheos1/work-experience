import {
    IonApp,
    IonButton,
    IonCard,
    IonContent,
    IonHeader,
    IonImg,
    IonInput,
    IonTitle,
    IonToolbar,
    IonModal,
    IonIcon,
    IonList,
    IonGrid,
    IonRow,
    IonCardContent,
    IonCardHeader,
    IonCardSubtitle,
    IonCol,
    IonItemGroup,
    IonItem,
    IonNote,
    IonLabel,
    IonBackButton,
    IonButtons,
    IonFab,
    IonFabButton,
    IonToast, 
    IonThumbnail
} from '@ionic/react';
import React, { useEffect, useRef, useState } from 'react';
import { helpCircleOutline as helpIcon, informationCircleOutline as infoIcon, add as addIcon, trashSharp as removeIcon, close as closeIcon, documentTextSharp as documentIcon, videocamSharp as videoIcon, cloudUploadSharp as saveIcon } from 'ionicons/icons';
import { storage, firestore } from '../firebase';
import { useAuth } from '../auth';
import { Editor } from '@tinymce/tinymce-react';
import { useParams } from 'react-router';
import { CommunicationItem, toCommunicationItem } from '../models/CommunicationItem'
import { Asset, Assets, toAssets } from '../models/Assets';

interface RouteParams {
    id: string
}

// This is out here so that blob can be used
async function saveAsset(blobUrl: string, userId: string, bucketId: string, newAsset: Asset, assets: Assets) {

    const pathName = newAsset.name.replace(/ /g, '_').toLowerCase()

    const fullPath = `/users/${userId}/${newAsset.type}/${pathName}`
    newAsset.fullPath = fullPath;

    const assetRef = storage.ref(fullPath)
    const response = await fetch(blobUrl);
    const blob = await response.blob();
    const snapshot = await assetRef.put(blob);
    const url = await snapshot.ref.getDownloadURL();
    newAsset.downloadUrl = url
    var found = false;
    if (newAsset.type === 'images') {
        // console.log("Add or Update Image", newAsset)
        assets.images.forEach((a, idx) => {
            if (a.fullPath === newAsset.fullPath) {
                assets.images[idx] = newAsset
                found = true;
            }
        })

        if (!found) { assets.images.push(newAsset) };

    } else {
        // console.log("Add or Update Media", newAsset)
        assets.media.forEach((a, idx) => {
            if (a.fullPath === newAsset.fullPath) {
                // a = { ...newAsset }
                assets.media[idx] = newAsset
                found = true;
            }
        })

        if (!found) { assets.media.push(newAsset) };
    }

    // console.log('saveAsset', bucketId, assets);
    const dbAssetRef = firestore.collection('users').doc(userId).collection('Assets').doc(bucketId)
    return await dbAssetRef.set({ ...assets })
        .then(function () {
            console.log("Asset successfully updated!");
        }).catch(function (error) {
            // The document probably doesn't exist.
            console.error("Error updating media: ", error);
            // setStatus({ loading: false, error: true })
        });
}

const EmailEditorPage: React.FC = () => {
    const { id } = useParams<RouteParams>();
    const [idName] = useState(id.replace(/_/g, ' '))
    // const history = useHistory();
    const { userId } = useAuth();
    const [bucketId, setBucketId] = useState('')
    const [showHelpText, setShowHelpText] = useState(false);
    const [showImageInfo, setShowImageInfo] = useState(false);
    const [showMediaInfo, setShowMediaInfo] = useState(false);
    const [showTextFieldInfo, setShowTextFieldInfo] = useState(false);
    const [showDisputeInfo, setShowDisputeInfo] = useState(false);
    const initEmail = `<p>Hi \${First Name}</p><p>We noticed a \${Charge Type} for \${Class Name} on \${Class Time}, in \${Class Location} with \${Class Instructor}.</p><p><strong><u>Write your email copy here!</u></strong></p><p>If you wish to request a refund <a href="\${dispute_link_url}">click here</a>.</p><table style="margin: 10px; border-collapse: collapse; float: left;"><tbody><tr><td rowspan="2"><img src="https://firebasestorage.googleapis.com/v0/b/mbo-no-show-ui-42d2e.appspot.com/o/favicon.png?alt=media&token=5193344a-22f7-4415-b239-a7e4e202a5c9" alt="Logo Thumbnail" /></td><td style="padding-left: 20px; font-size: 1.2em;"><strong>\${Location Manager}</strong></td></tr><tr><td style="padding-left: 20px;"><strong>\${Class Location}</strong></td></tr></tbody></table>`
    const imgPlaceholer = "/assets/placeholder.png";

    const initNewAsset: Asset = {
        name: '',
        type: '',
        downloadUrl: '',
        fullPath: '',
    }

    const [status, setStatus] = useState({ loading: true, error: false });
    const [showEmailToast, setShowEmailToast] = useState(false)
    const [showMediaToast, setShowMediaToast] = useState(false);
    const [mediaToastMessage, setMediaToastMessage] = useState('');
    const [pictureUrl, setPictureUrl] = useState(imgPlaceholer)
    const [tmpAssetUrl, setTmpAssetUrl] = useState('');
    const [showAddMedia, setShowAddMedia] = useState(false)
    const [showAddImage, setShowAddImage] = useState(false)
    const [isNewAssetValid, setIsNewAssetValid] = useState(false)
    const [showMediaUpdateModal, setShowMediaUpdateModal] = useState(false);
    const [showImageUpdateModal, setShowImageUpdateModal] = useState(false);
    const [showInformationText, setShowInformationText] = useState(false)
    const [communicationItem, setCommunicationItem] = useState<CommunicationItem>()
    const [newAsset, setNewAsset] = useState<Asset>(initNewAsset);
    const [assets, setAssets] = useState<Assets>();
    const fileInputRef = useRef<HTMLInputElement>();

    useEffect(() => () => {
        if (pictureUrl.startsWith('blob:')) {
            console.log('revokeFile', pictureUrl)
            URL.revokeObjectURL(pictureUrl);
        }
    }, [pictureUrl])

    useEffect(() => () => {
        if (tmpAssetUrl.startsWith('blob:')) {
            console.log('\ntmpAssetUrl revokeFile', tmpAssetUrl)
            URL.revokeObjectURL(tmpAssetUrl);
        }
    }, [tmpAssetUrl])

    useEffect(() => {
        return firestore.collection('users').doc(userId)
            .collection('CommunicationItems').doc(id).onSnapshot((doc) => {
                // console.log(doc.data())
                setCommunicationItem(toCommunicationItem(doc))
            })

    }, [userId, id]);

    useEffect(() => {
        const assetRef = firestore.collection('users').doc(userId).collection('Assets')
        return assetRef.limit(1).onSnapshot(({ docs }) => {
            docs.map((doc) => {
                // console.log(doc.id)
                setBucketId(doc.id)
                setAssets(toAssets(doc))
                return null
            })
        });
    }, [userId]);

    useEffect(() => {
        (newAsset.name !== '' && newAsset.type !== '') ? setIsNewAssetValid(true) : setIsNewAssetValid(false);
        // console.log("Asset Validator", isNewAssetValid, newAsset, tmpAssetUrl)
    }, [newAsset])

    useEffect(() => {
        if (typeof assets === 'undefined') {
            setStatus({ loading: true, error: false })
        } else {
            setStatus({ loading: false, error: false })
        }
    }, [assets])

    const handleEditorChange = (emailContent: string) => {
        // console.log('handleEditorChange', emailContent)
        setCommunicationItem({ ...communicationItem, body: emailContent })
    }

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files.length > 0) {
            const file = event.target.files.item(0);
            const picUrl = URL.createObjectURL(file);
            // console.log('createFile', picUrl)
            setPictureUrl(picUrl);
            setShowAddMedia(true)
            setTmpAssetUrl(picUrl)
        }
    }

    const handleSaveAsset = async () => {
        if (tmpAssetUrl.startsWith('blob')) {
            saveAsset(tmpAssetUrl, userId, bucketId, newAsset, assets)
            setNewAsset(initNewAsset)
            setTmpAssetUrl('')
            setShowAddMedia(false)
            setPictureUrl(imgPlaceholer)
            setMediaToastMessage(`Saving ${newAsset.name} now.`)
            setShowMediaToast(true)
        }
    }

    const handleDeleteAsset = async (deleteAsset: Asset) => {
        // console.log('handleDeleteAsset', deleteAsset)
        let tmp = { ...assets };

        setMediaToastMessage(`Removing ${deleteAsset.name} now.`)
        setShowMediaToast(true)

        if (deleteAsset.type === 'images') {
            // console.log("Delete Image")
            const filtered = assets.images.filter((a) => a.fullPath !== deleteAsset.fullPath)
            tmp = { ...assets, images: filtered }

        } else {
            // console.log("Delete Media")
            const filtered = assets.media.filter((a) => a.fullPath !== deleteAsset.fullPath)
            tmp = { ...assets, media: filtered }
        }

        storage.ref(deleteAsset.fullPath).delete().then(() => {
            console.log("Asset successfully deleted from storage!");
        }).catch((error) => {
            console.error('Error deleting from storage', error)
        });

        const dbAssetRef = firestore.collection('users').doc(userId).collection('Assets').doc(bucketId)
        dbAssetRef.set({ ...tmp })
            .then(function () {
                console.log("Asset successfully deleted from database!");
            }).catch(function (error) {
                // The document probably doesn't exist.
                console.error("Error deleting asset: ", error);
                // setStatus({ loading: false, error: true })
            });
    }

    const handleAddAssetCancel = () => {
        setTmpAssetUrl('')
        setNewAsset(initNewAsset)
        setPictureUrl(imgPlaceholer)
        setShowAddMedia(false)
        setShowAddImage(false)
    }

    const handleUpdate = async () => {
        const commsRef = firestore.collection('users').doc(userId).collection('CommunicationItems').doc(id)

        return await commsRef.set({ ...communicationItem })
            .then(function () {
                console.log("Email successfully updated!");
                setStatus({ loading: false, error: false })
                setShowEmailToast(true)
            })
            .catch(function (error) {
                // The document probably doesn't exist.
                console.error("Error updating email: ", error);
                setStatus({ loading: false, error: true })
            });
    }

    return (
        <IonApp>

            <IonFab vertical="bottom" horizontal="end">
                <IonFabButton onClick={handleUpdate}>
                    <IonIcon icon={saveIcon} />
                </IonFabButton>
            </IonFab>

            <IonToast
                isOpen={showEmailToast}
                position="top"
                onDidDismiss={() => setShowEmailToast(false)}
                message="Email have been saved."
                duration={2000}
            />

            <IonToast
                isOpen={showMediaToast}
                position="top"
                onDidDismiss={() => setShowMediaToast(false)}
                message={mediaToastMessage}
                duration={2000}
            />

            {/* Update Image Library Modal */}
            <IonModal
                isOpen={showImageUpdateModal}
                onDidDismiss={() => setShowImageUpdateModal(false)}
            >
                <IonToolbar>
                    <IonButton
                        slot="end"
                        color="primary"
                        fill="clear"
                        onClick={() => setShowImageUpdateModal(false)}>
                        <IonIcon slot="icon-only" icon={closeIcon} /></IonButton>
                    <IonTitle>Image Library</IonTitle>
                </IonToolbar>

                <IonContent className="ion-padding">
                    <IonGrid>
                        <IonRow class="ion-justify-content-center">
                            {/* Add Image Card */}
                            <IonCol size-lg='8'>
                                <IonCard>
                                    <IonGrid>
                                        <IonRow>
                                            <IonCol>
                                                <input hidden type='file' accept="image/png, image/jpeg"
                                                    ref={fileInputRef}
                                                    onChange={handleFileChange} />
                                                <IonImg src={pictureUrl} alt=""
                                                    style={{ cursor: 'pointer' }}
                                                    onClick={() => fileInputRef.current.click()} />
                                            </IonCol>
                                        </IonRow>
                                        <IonRow>
                                            <IonCol>
                                                <IonInput type="text"
                                                    value={newAsset?.name}
                                                    placeholder='Image Title'
                                                    onIonChange={(event) => setNewAsset({ ...newAsset, name: event.detail.value, type: 'images' })}
                                                />
                                            </IonCol>
                                        </IonRow>
                                        <IonRow>
                                            <IonCol>
                                                <IonButton
                                                    expand="full"
                                                    color="primary"
                                                    fill="clear"
                                                    onClick={() => showAddImage ? fileInputRef.current.click() : setShowAddImage(true)}><IonIcon slot="start" icon={addIcon} />
                                                Add Image</IonButton>
                                            </IonCol>
                                            <IonCol>
                                                <IonButton
                                                    disabled={!isNewAssetValid}
                                                    expand="full"
                                                    color="primary"
                                                    fill="clear"
                                                    onClick={handleSaveAsset}><IonIcon slot="start" icon={saveIcon} />
                                                Save Image</IonButton></IonCol>
                                            <IonCol>
                                                <IonButton
                                                    expand="block"
                                                    color="danger"
                                                    fill="clear"
                                                    onClick={handleAddAssetCancel}><IonIcon slot="start" icon={closeIcon} />
                                                Cancel</IonButton></IonCol>
                                        </IonRow>
                                    </IonGrid>
                                </IonCard>
                            </IonCol>
                        </IonRow>
                        <IonRow class="ion-justify-content-center">
                            {
                                assets?.images.map((item) =>
                                    <IonCol key={item.fullPath} size="auto">
                                        <IonCard>
                                            <IonCardHeader>
                                                <IonThumbnail slot="start">
                                                    <IonImg src={item.downloadUrl} alt={item.name} />
                                                </IonThumbnail>
                                                <IonCardSubtitle>{`${item.name}`}</IonCardSubtitle>
                                            </IonCardHeader>
                                            <IonCardContent class='ion-padding'>
                                                <IonImg src={item.downloadUrl} alt={item.name} />
                                            </IonCardContent>
                                            <IonButton
                                                color="danger"
                                                expand="block"
                                                fill="clear"
                                                onClick={() => handleDeleteAsset(item)}
                                            ><IonIcon slot="icon-only" icon={removeIcon} /></IonButton>
                                        </IonCard>
                                    </IonCol>
                                )}
                        </IonRow>
                    </IonGrid>
                </IonContent>
            </IonModal>


            {/* Update Media Library Modal */}
            <IonModal
                isOpen={showMediaUpdateModal}
                onDidDismiss={() => setShowMediaUpdateModal(false)}
            >
                <IonToolbar>
                    <IonButton
                        slot="end"
                        color="primary"
                        fill="clear"
                        onClick={() => setShowMediaUpdateModal(false)}>
                        <IonIcon slot="icon-only" icon={closeIcon} /></IonButton>
                    <IonTitle>Media Library</IonTitle>
                </IonToolbar>

                <IonContent className="ion-padding">
                    <IonItemGroup>
                        <IonItem button onClick={() => fileInputRef.current.click()}>
                            <IonLabel color="primary">Add Media</IonLabel>
                            <input hidden type='file' accept=".pdf, video/*"
                                ref={fileInputRef}
                                onChange={handleFileChange} />
                            <IonIcon slot="start" color="primary" icon={addIcon} />
                        </IonItem>

                        {!showAddMedia ? null :
                            <IonCard class='ion-padding'>

                                {/* Media Type*/}
                                <IonCardSubtitle class="ion-padding-top">Media Type</IonCardSubtitle>
                                <IonGrid>
                                    <IonRow class="ion-align-items-center">
                                        <IonCol>
                                            <IonButton
                                                expand="block"
                                                color="secondary"
                                                fill={newAsset?.type === 'document' ? 'solid' : "outline"}
                                                onClick={() => setNewAsset({ ...newAsset, type: 'document' })}>
                                                Document</IonButton>
                                        </IonCol>

                                        <IonCol>
                                            <IonButton
                                                expand="block"
                                                color="secondary"
                                                fill={newAsset?.type === 'video' ? 'solid' : "outline"}
                                                onClick={() => setNewAsset({ ...newAsset, type: 'video' })}>
                                                Video</IonButton>
                                        </IonCol>
                                    </IonRow>
                                </IonGrid>

                                {/* Media Title */}
                                <IonCardSubtitle class="ion-padding-top">Title</IonCardSubtitle>
                                <IonItem>
                                    <IonInput type="text"
                                        value={newAsset?.name}
                                        placeholder='Add a Title'
                                        onIonChange={(event) => setNewAsset({ ...newAsset, name: event.detail.value })}
                                    />
                                </IonItem>

                                <IonButton
                                    class="ion-padding"
                                    disabled={!isNewAssetValid}
                                    fill={!isNewAssetValid ? "outline" : "solid"}
                                    expand="block"
                                    color="primary"
                                    onClick={handleSaveAsset}><IonIcon slot="start" icon={saveIcon} />
                                    Save</IonButton>
                                {/* </IonItem> */}

                                <IonButton
                                    class="ion-padding"
                                    fill="clear"
                                    expand="block"
                                    color="danger"
                                    onClick={handleAddAssetCancel}><IonIcon slot="start" icon={closeIcon} />Cancel</IonButton>

                                {/* </IonList> */}
                            </IonCard>
                        }
                    </IonItemGroup>
                    <IonList>
                        {
                            assets?.media.map((item) =>
                                // console.log('Do I have media? ', item)
                                <IonItem key={item.fullPath}>
                                    <IonButton
                                        slot="end"
                                        color="danger"
                                        fill="clear"
                                        onClick={() => handleDeleteAsset(item)}>
                                        <IonIcon slot="end" icon={removeIcon} /></IonButton>
                                    <IonLabel>{item?.name}</IonLabel>
                                    <IonIcon icon={item.type === 'video' ? videoIcon : documentIcon} color="medium" slot="start" />
                                </IonItem>
                            )
                        }
                    </IonList>
                </IonContent>
            </IonModal>

            <IonHeader>
                <IonToolbar>
                    <IonTitle>Email Editor</IonTitle>
                    <IonButtons slot="start">
                        <IonBackButton defaultHref="/v1/settings" />
                    </IonButtons>
                </IonToolbar>
            </IonHeader>
            <IonContent className="ion-padding">

                <IonGrid fixed>
                    <IonRow>
                        <IonCol>
                            <IonItem onClick={() => setShowHelpText(!showHelpText)}>
                                <IonIcon slot="start" icon={helpIcon} color="warning"></IonIcon>
                                <IonLabel>How to use the Email Editor</IonLabel>
                            </IonItem>
                        </IonCol>
                    </IonRow>

                    {showHelpText
                        ?
                        <IonRow>
                            <IonCol>
                                <IonItemGroup>
                                    {/* <IonItems onClick={() => setShowHelpText(!setShowHelpText)} > */}
                                    <IonItem onClick={() => setShowHelpText(!showHelpText)}>
                                        <IonNote class="ion-padding-start">
                                            <p>The primary function of this page to write the emails that will be sent to your Clients.</p>
                                            <p>Your emails can be personalised by <b>inserting custom fields</b> into the email template via the <b>Toolbar</b></p>
                                            <p>There are <b>4</b> types of Custom Field</p>
                                        </IonNote>
                                    </IonItem>

                                    {/* Image Info */}
                                    <IonItem button onClick={() => setShowImageInfo(!showImageInfo)}>
                                        <IonLabel>Images</IonLabel>
                                    </IonItem>
                                    {showImageInfo ?
                                        <IonItem onClick={() => setShowImageInfo(!showImageInfo)}>
                                            <IonNote class="ion-padding-start">
                                                <p>You can update your Image Library by clicking <b>Update Library</b> in the <b>Toolbar</b></p>
                                                <p>To <b>upload</b> any images you want to have in your emails</p>
                                                <ul>
                                                    <li><p>Click <b>Update Image Library</b></p></li>
                                                    <li><p>Click <b>Add Image</b></p></li>
                                                    <li><p>Select a <em>.jpg</em> or <em>.png</em> file</p></li>
                                                    <li><p>Give it a <b>Title</b></p></li>
                                                    <li><p>and, click <b>Add Image</b></p></li>
                                                </ul>
                                                <hr />
                                                <p>The image will now be in your <em>Custom Fields</em> with the <b>Image Title</b> from the previous step.</p>
                                                <p>To <b>add</b> images to your email template.</p>
                                                <ul>
                                                    <li><p>Click <b>Insert Custom Field</b></p></li>
                                                    <li><p>Navigate to <b>Insert Image</b></p></li>
                                                    <li><p>and, select the <b>Image Title</b> from the menu options.</p></li>
                                                </ul>
                                                <hr />
                                                <p>To <b>update</b> images to your <em>Image Library</em> simply follow follow the same process as uploading.</p>
                                                <p>Just give the image the <b>Same Title</b> and this will update the image.</p>
                                                <hr />
                                                <p>To <b>delete</b> images from your <em>Image Library</em> simply click the <b>Red Bin</b> icon on the image card.</p>


                                            </IonNote>
                                        </IonItem>
                                        : null}

                                    {/* Media Info */}
                                    <IonItem button onClick={() => setShowMediaInfo(!showMediaInfo)}>
                                        <IonLabel>Media</IonLabel>
                                    </IonItem>
                                    {showMediaInfo ?
                                        <IonItem onClick={() => setShowMediaInfo(!showMediaInfo)}>
                                            <IonNote class="ion-padding-start">
                                                <p>Media is a <em>.pdf</em> or a <em>video</em> file</p>
                                                <p>Media items is inserted into the email template by a link.</p>
                                                <p>When the receiver of the email clicks the link they will be able to view the media item.</p>
                                                <p>You can update your Media Library by clicking <b>Update Library</b> in the <b>Toolbar</b></p>
                                                <ul>
                                                    <li><p>Click <b>Update Media Library</b></p></li>
                                                    <li><p>Click <b>Add Media</b></p></li>
                                                    <li><p>Select a <em>.jpg</em> or <em>.png</em> file</p></li>
                                                    <li><p>Give it a <b>Title</b></p></li>
                                                    <li><p>Select <b>Document</b> for pdf's</p></li>
                                                    <li><p>Select <b>Video</b> for video's</p></li>
                                                    <li><p>and, click <b>Save</b></p></li>
                                                </ul>
                                                <hr />
                                                <p>The media item will now be in your <em>Custom Fields</em> with the <b>Title</b> from the previous step.</p>
                                                <p>To <b>add</b> media items to your email template.</p>
                                                <ul>
                                                    <li><p>Click <b>Insert Custom Field</b></p></li>
                                                    <li><p>Navigate to <b>Insert Media</b></p></li>
                                                    <li><p>and, select the <b>Title</b> from the menu options.</p></li>
                                                </ul>
                                                <hr />
                                                <p>To <b>update</b> media items to your <em>Media Library</em> simply follow follow the same process as uploading.</p>
                                                <p>Just give the image the <b>Same Title</b> and this will update the image.</p>
                                                <hr />
                                                <p>To <b>delete</b> media items from your <em>Media Library</em> simply click the <b>Red Bin</b> icon.</p>
                                            </IonNote>
                                        </IonItem>
                                        : null}

                                    {/* Text Fields Info */}
                                    <IonItem button onClick={() => setShowTextFieldInfo(!showTextFieldInfo)}>
                                        <IonLabel>Text</IonLabel>
                                    </IonItem>
                                    {showTextFieldInfo ?
                                        <IonItem onClick={() => setShowTextFieldInfo(!showTextFieldInfo)}>
                                            <IonNote class="ion-padding-start">
                                                <p>Emails can be personalised by inserting <em>Text Fields</em>.</p>
                                                <p>A Text Field will add the field value relevent to the context when the email is sent.</p>
                                                <p>By inserting, for example, <b>First Name</b> into the template the <em>Clients First Name</em> will populate the <em>Text Field</em>.</p>
                                                <hr />
                                                <p>To <b>add</b> text field to your email template.</p>
                                                <ul>
                                                    <li><p>Click <b>Insert Custom Field</b></p></li>
                                                    <li><p>Navigate to <b>Insert Text Field</b></p></li>
                                                    <li><p>and, select the from the menu options.</p></li>
                                                </ul>
                                                <hr />
                                                <p><u>Note</u>: the text field will look like <b>{`\${Text Filed Name}`}</b> the email editor.</p>
                                                <p>This is the key for the field to be added into the email. If the syntax is tampered with it will create errors in the email template.</p>
                                                <hr />
                                                <p><u>Note</u>: the text fields can be styled.</p>
                                                <p>That is to say, A text field can be made bold, italic, coloured or any other style can be applied.</p>
                                            </IonNote>
                                        </IonItem>
                                        : null}

                                    {/* Dispute Link Info */}
                                    <IonItem button onClick={() => setShowDisputeInfo(!showDisputeInfo)}>
                                        <IonLabel>Dispute Link</IonLabel>
                                    </IonItem>
                                    {showDisputeInfo ?
                                        <IonItem onClick={() => setShowDisputeInfo(!showDisputeInfo)}>
                                            <IonNote class="ion-padding-start">
                                                {/* <p><b>Dispute Link</b></p> */}
                                                <p>The <em>Dispute Link</em> will open a form so that the <em>Client</em> can request a pardon.</p>
                                                <p>When a <b>Refund Request</b> is submitted it will show on the <b>Home Page</b>.</p>
                                                <p>If the request is <b>approved</b> the <em>Client</em> <b>will not be Charged</b></p>
                                                <p>If <b>denied</b>, the charge will be applied.</p>
                                                <hr />
                                                <p><u>Note</u>: if the request <b>is</b> approved <b>after</b> the Charge has been applied.</p>
                                                <p>A Refund will show on the <b>Home Page</b>.</p>
                                            </IonNote>
                                        </IonItem>
                                        : null}
                                </IonItemGroup>
                            </IonCol>
                        </IonRow>
                        : null}

                    <IonRow>
                        <IonCol>
                            {/* Information Help Bar */}
                            <IonList>
                                <IonItem onClick={() => setShowInformationText(!showInformationText)}>
                                    <IonIcon slot="start" icon={infoIcon} color="primary"></IonIcon>
                                    <IonLabel
                                        color="primary"
                                        class="ion-text-capitalize">
                                        <strong>{`${idName}`}</strong>
                                    </IonLabel>
                                </IonItem>

                                {/* pre_launch_email */}
                                {showInformationText && id === 'pre_launch_warning_email'
                                    ?
                                    <IonItem onClick={() => setShowInformationText(!showInformationText)}>
                                        <IonNote class="ion-padding-start">
                                            <p>This email is <b>sent when</b>:</p>
                                            <p>Pre-Launch Mode <b>is <u>on</u></b></p>
                                            <p><b>and</b> a No Show or Late Cancel <b>has</b> occurred</p>
                                        </IonNote>
                                    </IonItem>
                                    : null}

                                {/* late_cancel_warning */}
                                {showInformationText && id === 'late_cancel_warning_email'
                                    ?
                                    <IonItem onClick={() => setShowInformationText(!showInformationText)}>
                                        <IonNote class="ion-padding-start">
                                            <p>This email is <b>sent when</b>:</p>
                                            <p>Pre-Launch Mode <b>is <u>off</u></b></p>
                                            <p><b>and</b> a Late Cancel <b>has</b> occurred</p>
                                            <p><b>and</b> the Late Cancel <b>has been Forgiven</b></p>
                                        </IonNote>
                                    </IonItem>
                                    : null}

                                {/* late_cancel_charge */}
                                {showInformationText && id === 'late_cancel_charge_email'
                                    ?
                                    <IonItem onClick={() => setShowInformationText(!showInformationText)}>
                                        <IonNote class="ion-padding-start">
                                            <p>This email is <b>sent when</b>:</p>
                                            <p>Pre-Launch Mode <b>is <u>off</u></b></p>
                                            <p><b>and</b> a Late Cancel <b>has</b> occurred</p>
                                            <p><b>and</b> a Charge <b>is Pending</b></p>
                                        </IonNote>
                                    </IonItem>
                                    : null}

                                {/* no_show_warning */}
                                {showInformationText && id === 'no_show_warning_email'
                                    ?
                                    <IonItem onClick={() => setShowInformationText(!showInformationText)}>
                                        <IonNote class="ion-padding-start">
                                            <p>This email is <b>sent when</b>:</p>
                                            <p>Pre-Launch <b>is <u>off</u></b></p>
                                            <p><b>and</b> a No Show <b>has occurred</b></p>
                                            <p><b>and</b> the No Show <b>has been Forgiven</b></p>
                                        </IonNote>
                                    </IonItem>
                                    : null}

                                {/* no_show_charge */}
                                {showInformationText && id === 'no_show_charge_email'
                                    ?
                                    <IonItem onClick={() => setShowInformationText(!showInformationText)}>
                                        <IonNote class="ion-padding-start">
                                            <p>This email is <b>sent when</b>:</p>
                                            <p>Pre-Launch Mode <b>is <u>off</u></b></p>
                                            <p><b>and</b> a No Show <b>has</b> occurred</p>
                                            <p><b>and</b> a Charge <b>is Pending</b></p>
                                        </IonNote>
                                    </IonItem>
                                    : null}
                            </IonList>

                            {/* Email Text Editor */}
                            {status.loading ? null :
                                <Editor
                                    initialValue={communicationItem?.body}
                                    apiKey="w5nw91hdtraf1ogrw57lt7mv9t4q5mj0x9y5b6zfos9bgtpa"
                                    init={{
                                        height: 500,
                                        plugins: [
                                            'advlist autolink lists link image',
                                            'charmap print preview anchor help',
                                            'searchreplace visualblocks code',
                                            'insertdatetime media table paste wordcount'
                                        ],
                                        toolbar:
                                            'image | undo redo | formatselect | bold italic | alignleft aligncenter alignright | bullist numlist outdent indent | forecolor backcolor | table | table tabledelete | tableprops tablerowprops tablecellprops | tableinsertrowbefore tableinsertrowafter tabledeleterow | tableinsertcolbefore tableinsertcolafter tabledeletecol | code | help',

                                        menubar: 'custom | other',
                                        menu: {
                                            custom: { title: 'Insert Custom Field', items: 'resetTemplate insertImage insertMedia insertDynamicField insertDisputeLink' },
                                            other: { title: 'Update Library', items: 'updateImageLibrary updateMediaLibrary' }
                                        },

                                        setup: (editor) => {
                                            editor.ui.registry.addMenuItem('resetTemplate', {
                                                text: 'Reset to Template',
                                                onAction: function () {
                                                    // console.log('resetTemplate')
                                                    setCommunicationItem({ ...communicationItem, body: initEmail })
                                                }
                                            });
                                            editor.ui.registry.addMenuItem('updateImageLibrary', {
                                                text: 'Update Image Library',
                                                onAction: function () {
                                                    // console.log('updateImageLibrary')
                                                    setShowImageUpdateModal(true)
                                                }
                                            });
                                            editor.ui.registry.addMenuItem('updateMediaLibrary', {
                                                text: 'Update Media Library',
                                                onAction: function () {
                                                    // console.log('updateMediaLibrary')
                                                    setShowMediaUpdateModal(true)
                                                }
                                            });
                                            editor.ui.registry.addMenuItem('insertDisputeLink', {
                                                text: 'Dispute Link',
                                                onAction: function () {
                                                    editor.insertContent(` <a href='\${dispute_link_url}'>dispute text</a> `);
                                                }
                                            });
                                            editor.ui.registry.addNestedMenuItem('insertImage', {
                                                text: 'Insert Image',
                                                getSubmenuItems: function () {
                                                    let items = []
                                                    assets?.images.map((a) => {
                                                        return items.push({
                                                            type: 'menuitem',
                                                            text: a.name,
                                                            onAction: function () {
                                                                editor.insertContent(`<img src='${a.downloadUrl}' alt='${a.name}' />`);
                                                            }
                                                        })
                                                    })
                                                    return items
                                                }
                                            });
                                            editor.ui.registry.addNestedMenuItem('insertMedia', {
                                                text: 'Insert Media',
                                                getSubmenuItems: function () {
                                                    let items = []
                                                    assets?.media.map((a) => {
                                                        return items.push({
                                                            type: 'menuitem',
                                                            text: a.name,
                                                            onAction: function () {
                                                                editor.insertContent(` <a href='${a.downloadUrl}'>link text</a> `);
                                                            }
                                                        })
                                                    })
                                                    // console.log(items)
                                                    return items
                                                }
                                            });
                                            editor.ui.registry.addNestedMenuItem('insertDynamicField', {
                                                text: 'Insert Text Field',
                                                getSubmenuItems: function () {
                                                    return [
                                                        // First Name
                                                        {
                                                            type: 'menuitem',
                                                            text: 'First Name',
                                                            onAction: function () {
                                                                editor.insertContent(`\${First Name}`);
                                                            }
                                                        },
                                                        // Last Name
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Last Name',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Last Name}`);
                                                            }
                                                        },
                                                        // Charge
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Charge Type',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Charge Type}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Charge Status',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Charge Status}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Charge Billing Date',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Charge Billing Date}`);
                                                            }
                                                        },
                                                        // Class
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Class Day',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Class Day}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Class Instructor',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Class Instructor}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Class Location',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Class Location}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Class Name',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Class Name}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Class Time',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Class Time}`);
                                                            }
                                                        },
                                                        // Location
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Location Manager',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Location Manager}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Location Email',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Location Email}`);
                                                            }
                                                        },
                                                        {
                                                            type: 'menuitem',
                                                            text: 'Location Phone',
                                                            onAction: function () {
                                                                editor.insertContent(`\${Location Phone}`);
                                                            }
                                                        },
                                                    ];
                                                }
                                            });
                                        }
                                    }}
                                    outputFormat='html'
                                    value={communicationItem?.body}
                                    onEditorChange={handleEditorChange}
                                />
                            }
                        </IonCol>
                    </IonRow>
                </IonGrid>
            </IonContent>
        </IonApp>
    );
};

export default EmailEditorPage;